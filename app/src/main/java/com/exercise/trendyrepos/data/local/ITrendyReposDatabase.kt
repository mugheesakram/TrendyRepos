package com.exercise.trendyrepos.data.local

import com.exercise.trendyrepos.data.responses.Repo

interface ITrendyReposDatabase {
    suspend fun getTopRepos(): List<Repo>
    suspend fun insertTopRepos(repos: List<Repo>)
}