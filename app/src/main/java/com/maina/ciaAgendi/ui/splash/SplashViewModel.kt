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

package com.maina.ciaAgendi.ui.splash

import com.maina.ciaAgendi.data.db.HymnalDatabase
import com.maina.ciaAgendi.ui.base.ScopedViewModel
import com.maina.ciaAgendi.ui.base.SingleLiveEvent
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.android.Main
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import timber.log.Timber
import javax.inject.Inject

class SplashViewModel @Inject constructor(database: HymnalDatabase) : ScopedViewModel() {

    var initialised = SingleLiveEvent<Boolean>()

    init {

        launch {
            val list = database.hymnalDao().listAll()
            val hymns = database.hymnsDao().getHymns("sdah")

            Timber.d("HYMNALS: ${list.size}")
            Timber.d("ENG-HYMNS: ${hymns.size}")

            withContext(Dispatchers.Main) {
                initialised.postValue(true)
            }
        }
    }
}