package com.exercise.trendyrepos.utils.base

import androidx.lifecycle.MutableLiveData

abstract class BaseState : IBase.State {
    override var uiState: MutableLiveData<UIState> = MutableLiveData()
}
