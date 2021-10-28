package com.exercise.trendyrepos.utils.base

import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData

abstract class BaseState : BaseObservable(), IBase.State {
    override var loaderState: MutableLiveData<Any?> = MutableLiveData()
}
