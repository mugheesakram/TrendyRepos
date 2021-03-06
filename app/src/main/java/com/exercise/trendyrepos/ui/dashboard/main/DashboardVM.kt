package com.exercise.trendyrepos.ui.dashboard.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.exercise.trendyrepos.data.IDataInfo
import com.exercise.trendyrepos.data.base.ApiResponse
import com.exercise.trendyrepos.data.dto.Repo
import com.exercise.trendyrepos.utils.base.BaseViewModel
import com.exercise.trendyrepos.utils.base.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DashboardVM @Inject constructor(
    private val repository: IDataInfo,
) : BaseViewModel(),
    IDashboard.ViewModel {
    private var _uiState: MutableLiveData<UIState> = MutableLiveData()
    override var uiState: LiveData<UIState> = _uiState

    private val _repos: MutableLiveData<MutableList<Repo>> = MutableLiveData()
    override var repos: LiveData<MutableList<Repo>> = _repos

    override fun getTopGithubRepos(query: String, isRefresh: Boolean) {
        launch {
            _uiState.postValue(UIState.Loading)
            val response = repository.getTopGithubRepositories(query, isRefresh)
            withContext(Dispatchers.Main) {
                when (response) {
                    is ApiResponse.Success -> {
                        _repos.value = response.data.repos as MutableList<Repo>?
                    }
                    is ApiResponse.Error -> {
                        _repos.value = mutableListOf()
                        _uiState.value = UIState.Error(response.error.message)
                    }
                }
            }
        }
    }

    override fun refreshData() {
        getTopGithubRepos("language=+sort:stars", true)
    }
}