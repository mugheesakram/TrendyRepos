package com.exercise.trendyrepos.data

import com.exercise.trendyrepos.data.base.ApiResponse
import com.exercise.trendyrepos.data.dto.GithubRepos
import com.exercise.trendyrepos.data.local.ITrendyReposDatabase
import com.exercise.trendyrepos.data.remote.ReposApi
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val localRepository: ITrendyReposDatabase,
    private val remoteRepository: ReposApi
) : IDataInfo {
    override suspend fun getTopGithubRepositories(
        query: String,
        isRefresh: Boolean
    ): ApiResponse<GithubRepos> {
        val repos = localRepository.getTopRepos()
        return when {
            !isRefresh && repos.isNotEmpty() -> {
                ApiResponse.Success(
                    200,
                    GithubRepos(repos = repos)
                )
            }
            else -> {
                val response = remoteRepository.getTopApi(query)
                if (response is ApiResponse.Success) {
                    response.data.repos?.let { localRepository.insertTopRepos(it) }
                }
                response
            }
        }
    }
}