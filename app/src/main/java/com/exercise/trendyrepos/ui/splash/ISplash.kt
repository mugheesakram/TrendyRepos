package com.exercise.trendyrepos.ui.splash

import com.exercise.trendyrepos.utils.base.IBase

interface ISplash {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>
    interface State : IBase.State
}