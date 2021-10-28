package com.exercise.trendyrepos.data.base

import retrofit2.Response

internal interface IRepository {
    suspend fun <T : ApiResponse> executeSafely(call: suspend () -> Response<T>): RetroApiResponse<T>
}