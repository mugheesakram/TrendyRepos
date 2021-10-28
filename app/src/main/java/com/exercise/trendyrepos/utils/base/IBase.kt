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
        var loaderState: MutableLiveData<Any?>
    }
}
