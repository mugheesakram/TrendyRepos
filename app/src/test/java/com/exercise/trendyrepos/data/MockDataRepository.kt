package com.exercise.trendyrepos.data

import com.exercise.trendyrepos.Utils.readFileFromTestResources
import com.exercise.trendyrepos.data.base.ApiResponse
import com.exercise.trendyrepos.data.base.error.ApiError
import com.exercise.trendyrepos.data.dto.GithubRepos
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class MockDataRepository : IDataInfo {
    override suspend fun getTopGithubRepositories(
        query: String,
        isRefresh: Boolean
    ): ApiResponse<GithubRepos> {
        val response = getMockResponse()
        return if (query.isNotBlank())
            ApiResponse.Success(200, response)
        else
            ApiResponse.Error(ApiError(400, "Bad request"))
    }

    private fun getMockResponse(fileName: String = "success_response_api.json"): GithubRepos {
        val gson = GsonBuilder().create()
        val itemType = object : TypeToken<GithubRepos>() {}.type
        return gson.fromJson(readFileFromTestResources("success_response_api.json"), itemType)
    }
}