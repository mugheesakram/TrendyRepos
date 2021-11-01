package com.exercise.trendyrepos.data.dto


import androidx.room.Entity
import com.exercise.trendyrepos.data.base.BaseResponse
import com.google.gson.annotations.SerializedName

@Entity(tableName = "github_repos")
data class GithubRepos(
    @SerializedName("incomplete_results")
    var incompleteResults: Boolean? = null,
    @SerializedName("items")
    var repos: List<Repo>? = null,
    @SerializedName("total_count")
    var totalCount: Int? = null
) : BaseResponse()