/*
 * Copyright (c) 2018. Tony Maina.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.maina.ciaAgendi.ui.home.navigation

import android.arch.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.maina.ciaAgendi.data.db.HymnalDatabase
import com.maina.ciaAgendi.data.model.Collections
import com.maina.ciaAgendi.data.model.Hymnal
import com.maina.ciaAgendi.data.model.LocalHymnal
import com.maina.ciaAgendi.ui.base.ScopedViewModel
import com.maina.ciaAgendi.ui.base.SingleLiveEvent
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.android.Main
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import timber.log.Timber
import javax.inject.Inject

class NavigationViewModel @Inject constructor(private val fireStore: FirebaseFirestore,
                                              private val database: HymnalDatabase) : ScopedViewModel() {

    var hymnals = MutableLiveData<List<Hymnal>>()
    var updatedHymnal = SingleLiveEvent<Hymnal>()

    init {
        updateHymnalsTask()
    }

    private fun updateHymnalsTask() {

        launch {

            val localHymnals = database.hymnalDao().listAll()
            val result = arrayListOf<Hymnal>()

            localHymnals.forEach { local ->
                val hymnal = Hymnal(local.name, local.language, local.code)
                hymnal.available = true
                result.add(hymnal)
            }

            withContext(Dispatchers.Main) {
                hymnals.value = result
            }

            val collectionReference = fireStore.collection(Collections.HYMNALS)
            collectionReference.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val snapshot = task.result

                    snapshot?.forEach { data ->
                        val hymnal = data.toObject(Hymnal::class.java)

                        if (!result.contains(hymnal)) {
                            if (hymnal.code == "sdah") { //default hymnal
                                hymnal.available = true
                            }
                            result.add(hymnal)
                        }
                    }

                    result.sort()

                    hymnals.postValue(result)

                } else {
                    Timber.e(task.exception)
                }
            }
        }
    }

    fun hymnalSelected(hymnal: Hymnal) {
        if (hymnal.available) {

            //TODO: Update selected hymnal and close navigation screen

        } else {

            launch {

                val local = LocalHymnal.fromHymnal(hymnal)

                database.hymnalDao().insert(local)

                //TODO: Download file then make hymnal available local
                Thread.sleep(200)

                withContext(Dispatchers.Main) {
                    val list = ArrayList(hymnals.value ?: emptyList())
                    if (list.isEmpty()) return@withContext

                    list.find { it.code == hymnal.code }?.available = true
                    list.sortByDescending { it.available }

                    hymnals.postValue(list)

                    updatedHymnal.postValue(hymnal)
                }
            }
        }
    }
}