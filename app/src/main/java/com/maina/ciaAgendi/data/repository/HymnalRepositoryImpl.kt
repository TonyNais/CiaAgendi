package com.maina.ciaAgendi.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.maina.ciaAgendi.data.db.HymnalDatabase
import com.maina.ciaAgendi.data.model.Collections
import com.maina.ciaAgendi.data.model.Hymnal
import timber.log.Timber

class HymnalRepositoryImpl constructor(private val database: HymnalDatabase,
                                       private val fireStore: FirebaseFirestore) : HymnalRepository {

    private var hymnalsData = MutableLiveData<List<Hymnal>>()

    override fun listHymnals(): LiveData<List<Hymnal>> {

        return hymnalsData
    }

    private fun fetchRemote() {
        val task = fireStore.collection(Collections.HYMNALS).get()
        task.addOnCompleteListener {

            if (it.isSuccessful) {

                val snapshot = task.result

                val list = ArrayList(hymnalsData.value ?: emptyList())

                snapshot?.forEach { data ->
                    val hymnal = data.toObject(Hymnal::class.java)
                    if (!list.contains(hymnal)) {
                        list.add(hymnal)
                    }
                }

                hymnalsData.value = list

            } else {
                Timber.e(it.exception)
            }
        }
    }
}