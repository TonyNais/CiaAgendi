package com.maina.ciaAgendi.data.repository

import android.arch.lifecycle.LiveData
import com.maina.ciaAgendi.data.model.Hymnal

interface HymnalRepository {

    fun listHymnals(): LiveData<List<Hymnal>>
}