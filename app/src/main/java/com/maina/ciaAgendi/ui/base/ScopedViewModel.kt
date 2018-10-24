package com.maina.ciaAgendi.ui.base

import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.Job
import kotlin.coroutines.experimental.CoroutineContext

open class ScopedViewModel : ViewModel(), CoroutineScope {
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}