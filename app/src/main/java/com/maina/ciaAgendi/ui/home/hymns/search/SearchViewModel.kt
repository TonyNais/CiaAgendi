package com.maina.ciaAgendi.ui.home.hymns.search

import com.maina.ciaAgendi.data.db.HymnalDatabase
import com.maina.ciaAgendi.data.model.Hymn
import com.maina.ciaAgendi.ui.base.ScopedViewModel
import com.maina.ciaAgendi.ui.base.SingleLiveEvent
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.IO
import kotlinx.coroutines.experimental.android.Main
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val database: HymnalDatabase) : ScopedViewModel() {

    var searchResults = SingleLiveEvent<List<Hymn>>()

    fun search(query: String?) {

        if (query == null) {
            searchResults.postValue(emptyList())
            return
        }

        launch {
            withContext(Dispatchers.IO) {
                val results = database.hymnsDao().search("%$query%")

                withContext(Dispatchers.Main) {
                    searchResults.postValue(results)
                }
            }
        }
    }
}