package com.exercise.trendyrepos.ui.dashboard.main

import androidx.lifecycle.LiveData
import com.exercise.trendyrepos.data.dto.Repo
import com.exercise.trendyrepos.ui.dashboard.main.adapter.RepositoryAdapter
import com.exercise.trendyrepos.utils.base.IBase

interface IDashboard {
    interface View : IBase.View<ViewModel> {
        val adaptor: RepositoryAdapter
        fun viewModelObservers()
        fun setGithubRepos(list: MutableList<Repo>)
        fun showDataView()
        fun showLoadingView()
        fun showErrorView()
        fun loadingView(show: Boolean)
    }

    interface ViewModel : IBase.ViewModel<State> {
        val repos: LiveData<MutableList<Repo>>
        fun getTopGithubRepos(query: String, isRefresh: Boolean)
        fun refreshData()
    }

    interface State : IBase.State
}