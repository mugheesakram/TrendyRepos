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
        viewModel.getTopGithubRepos("language=+sort:stars", false)
        viewModelObservers()
        initListeners()
    }

    private fun initListeners() {
        binding.btnRetry.setOnClickListener {
            viewModel.refreshData()
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshData()
        }
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
        loadingView(false)
        binding.errorView.toGone()
    }

    override fun showLoadingView() {
        loadingView(true)
        binding.errorView.toGone()
        binding.recyclerView.toGone()
    }

    override fun showErrorView() {
        binding.errorView.toVisible()
        loadingView(false)
        binding.recyclerView.toGone()
    }

    override fun loadingView(show: Boolean) {
        if (show) {
            binding.shimmerLayout.shimmerFrameLayout.toVisible()
            binding.shimmerLayout.shimmerFrameLayout.startShimmer()
        } else {
            binding.shimmerLayout.shimmerFrameLayout.toGone()
            binding.shimmerLayout.shimmerFrameLayout.stopShimmer()
        }
    }

    override fun viewModelObservers() {
        viewModel.repos.observe(this, ::setGithubRepos)
        viewModel.uiState.observe(this, {
            binding.swipeRefreshLayout.isRefreshing = false
            handleUIState(it)
        })
    }
}
