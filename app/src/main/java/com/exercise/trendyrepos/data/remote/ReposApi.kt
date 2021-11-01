package com.exercise.trendyrepos.data.remote

import com.exercise.trendyrepos.data.base.ApiResponse
import com.exercise.trendyrepos.data.responses.GithubRepos

interface ReposApi {
    suspend fun getTopApi(query:String):ApiResponse<GithubRepos>
}