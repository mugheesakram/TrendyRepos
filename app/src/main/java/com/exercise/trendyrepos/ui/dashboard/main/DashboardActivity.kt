package com.exercise.trendyrepos.ui.dashboard.main

import android.os.Bundle
import androidx.activity.viewModels
import com.exercise.trendyrepos.R
import com.exercise.trendyrepos.data.dto.Repo
import com.exercise.trendyrepos.databinding.ActivityDashboardBinding
import com.exercise.trendyrepos.ui.dashboard.main.adapter.RepositoryAdapter
import com.exercise.trendyrepos.utils.base.BaseActivity
import com.exercise.trendyrepos.utils.base.UIState
import com.exercise.trendyrepos.utils.extension.toGone
import com.exercise.trendyrepos.utils.extension.toVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : BaseActivity<ActivityDashboardBinding, IDashboard.ViewModel>(),
    IDashboard.View {
    override val viewModel: IDashboard.ViewModel by viewModels<DashboardVM>()
    override fun getLayoutId() = R.layout.activity_dashboard
    override fun getViewBinding(): ActivityDashboardBinding =
        ActivityDashboardBinding.inflate(layoutInflater)

    override val adaptor: RepositoryAdapter = RepositoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getTopGithubRepos("language=+sort:stars")
        viewModelObservers()
    }

    override fun setGithubRepos(list: MutableList<Repo>) {
        if (!(list.isNullOrEmpty())) {
            showDataView()
            adaptor.setList(list)
            binding.recyclerView.adapter = adaptor
        } else {
            showErrorView()
        }
    }

    private fun handleUIState(it: UIState) {
        when (it) {
            is UIState.Loading -> {
                showLoadingView()
            }
            is UIState.Error -> {
                showErrorView()
            }
        }
    }

    override fun showDataView() {
        binding.recyclerView.toVisible()
        // step2 hide shimmerview // loading
        // step3 hide error view
    }

    override fun showLoadingView() {
        // show shimmer
    }

    override fun showErrorView() {
        // show error view
        binding.recyclerView.toGone()
    }

    override fun viewModelObservers() {
        viewModel.repos.observe(this, ::setGithubRepos)
        viewModel.viewState.uiState.observe(this, ::handleUIState)
    }
}
