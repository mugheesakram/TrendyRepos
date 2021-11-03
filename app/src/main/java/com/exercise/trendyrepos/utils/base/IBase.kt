package com.exercise.trendyrepos.utils.base

interface IBase {
    interface View<V : ViewModel> {
        val viewModel: V
    }

    interface ViewModel
}

sealed class UIState {
    object Loading : UIState()
    data class Error(val error: String? = null) : UIState()
}
