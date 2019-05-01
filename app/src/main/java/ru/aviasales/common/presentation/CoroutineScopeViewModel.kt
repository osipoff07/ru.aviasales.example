package ru.aviasales.common.presentation

import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class CoroutineScopeViewModel(
    protected val defaultJob: Job = Job(),
    protected val uiContext: CoroutineContext = Dispatchers.Main,
    protected val ioContext: CoroutineContext = Dispatchers.IO
): ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext by lazy { uiContext + defaultJob }

    override fun onCleared() {
        super.onCleared()
        defaultJob.cancel()
    }
}