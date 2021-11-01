package com.exercise.trendyrepos.data.remote

import com.exercise.trendyrepos.data.base.BaseRepository
import com.exercise.trendyrepos.data.base.RetroNetwork
import javax.inject.Inject

class RemoteRepository @Inject
constructor(
    serviceGenerator: RetroNetwork,
) : BaseRepository(), ReposApi {
    private val api: ReposService = serviceGenerator.createService(ReposService::class.java)

    override suspend fun getTopApi(query: String) = executeSafely(
        call = {
            api.getTopRepos(query)
        }
    )
}