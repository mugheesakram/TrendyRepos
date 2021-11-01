package com.exercise.trendyrepos.data

import com.exercise.trendyrepos.data.base.ApiResponse
import com.exercise.trendyrepos.data.local.LocalRepository
import com.exercise.trendyrepos.data.responses.GithubRepos
import com.exercise.trendyrepos.data.remote.RemoteRepository
import com.exercise.trendyrepos.data.remote.ReposApi
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) : ReposApi {
    override suspend fun getTopApi(query: String): ApiResponse<GithubRepos> {
        val repos = localRepository.getTopRepos()
        return when {
            repos.isNotEmpty() -> {
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