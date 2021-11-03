package com.exercise.trendyrepos.utils.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


abstract class BaseViewModel : ViewModel(), IBase.ViewModel {
    fun launch(dispatcher: CoroutineContext = Dispatchers.IO, block: suspend () -> Unit) {
        viewModelScope.launch(dispatcher) { block() }
    }
}
