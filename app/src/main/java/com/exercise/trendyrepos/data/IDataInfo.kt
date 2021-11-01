package com.exercise.trendyrepos.data

import com.exercise.trendyrepos.data.base.ApiResponse
import com.exercise.trendyrepos.data.dto.GithubRepos

interface IDataInfo {
    suspend fun getTopGithubRepositories(
        query: String,
        isRefresh: Boolean
    ): ApiResponse<GithubRepos>
}