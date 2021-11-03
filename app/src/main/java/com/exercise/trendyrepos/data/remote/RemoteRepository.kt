package com.exercise.trendyrepos.data.remote

import com.exercise.trendyrepos.data.base.BaseRepository
import javax.inject.Inject

class RemoteRepository @Inject
constructor(
    service: ReposService
) : BaseRepository(), ReposApi {
    private val api = service
    override suspend fun getTopApi(query: String) = executeSafely(
        call = {
            api.getTopRepos(query)
        }
    )
}