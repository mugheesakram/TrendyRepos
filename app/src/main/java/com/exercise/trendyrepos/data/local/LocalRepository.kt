package com.exercise.trendyrepos.data.local

import com.exercise.trendyrepos.data.responses.Repo
import javax.inject.Inject

class LocalRepository @Inject constructor(private val trendyDao: TrendyDao) :
    ITrendyReposDatabase {
    override suspend fun getTopRepos() = trendyDao.getAllTopRepos()

    override suspend fun insertTopRepos(repos: List<Repo>) =
        trendyDao.insertAllRepos(repos)
}