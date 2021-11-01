package com.exercise.trendyrepos.data.remote

import com.exercise.trendyrepos.data.dto.GithubRepos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ReposService {
    @GET("search/repositories")
    suspend fun getTopRepos(@Query("q") query: String): Response<GithubRepos>
}