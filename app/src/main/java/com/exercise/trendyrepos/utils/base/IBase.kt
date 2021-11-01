package com.exercise.trendyrepos.utils.base

import androidx.lifecycle.MutableLiveData

interface IBase {
    interface View<V : ViewModel<*>> {
        val viewModel: V
    }

    interface ViewModel<S : State> :
        ILifecycle {
        val viewState: S
    }

    interface State {
        var uiState: MutableLiveData<UIState>
    }
}

sealed class UIState {
    object Loading : UIState()
    data class Error(val error: String? = null) : UIState()
}
