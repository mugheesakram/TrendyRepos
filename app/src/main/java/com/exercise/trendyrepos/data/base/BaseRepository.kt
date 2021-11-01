package com.exercise.trendyrepos.data.base

import com.exercise.trendyrepos.data.base.error.ApiError
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import com.google.gson.stream.MalformedJsonException as MalformedJsonException1

const val MALFORMED_JSON_EXCEPTION_CODE = 0

abstract class BaseRepository : IRepository {

    override suspend fun <T : BaseResponse> executeSafely(call: suspend () -> Response<T>): ApiResponse<T> {
        try {
            val response: Response<T> = call.invoke()
            if (response.isSuccessful) {
                return ApiResponse.Success(response.code(), response.body()!!)
            }

            // Check if this is not a server side error (4** or 5**) then return error instead of success
            return ApiResponse.Error(detectError(response))

        } catch (exception: MalformedJsonException1) {
            return ApiResponse.Error(
                ApiError(
                    MALFORMED_JSON_EXCEPTION_CODE,
                    exception.localizedMessage ?: ""
                )
            )
        } catch (exception: Exception) {
            return ApiResponse.Error(
                ApiError(
                    getDefaultCode(),
                    exception.localizedMessage ?: ""
                )
            )
        }
    }

    private fun <T : BaseResponse> detectError(response: Response<T>): ApiError {
        return when (response.code()) {
            403 -> getApiError(mapError(NetworkErrors.Forbidden, response.code()))
            404 -> getApiError(mapError(NetworkErrors.NotFound, response.code()))
            502 -> getApiError(mapError(NetworkErrors.BadGateway, response.code()))
            504 -> getApiError(mapError(NetworkErrors.NoInternet, response.code()))
            in 400..500 -> getApiError(
                mapError(
                    NetworkErrors.InternalServerError(response.errorBody()?.string()),
                    response.code()
                )
            )
            -1009 -> getApiError(mapError(NetworkErrors.NoInternet, response.code()))
            -1001 -> getApiError(mapError(NetworkErrors.RequestTimedOut, response.code()))
            else -> {
                getApiError(mapError(NetworkErrors.UnknownError(), response.code()))
            }
        }
    }

    private fun getError(response: String?): ServerError {
        response?.let {
            if (it.isNotBlank()) {
                try {
                    val obj = JSONObject(it)

                    if (obj.has("error")) {
                        val objError = obj.getJSONObject("error")
                        val code = objError.getInt("code")
                        val message = objError.getString("info")
                        return ServerError(
                            code,
                            message,
                        )
                    }
                } catch (e: JSONException) {
                    ServerError(getDefaultCode(), e.localizedMessage)
                }
            }
        }
        return ServerError(getDefaultCode(), getDefaultMessage())
    }

    private fun getApiError(error: ServerError): ApiError {
        return ApiError(
            error.code ?: getDefaultCode(),
            error.message ?: getDefaultMessage()
        )
    }

    private fun mapError(error: NetworkErrors, code: Int = 0): ServerError {
        return when (error) {

            is NetworkErrors.NoInternet -> ServerError(
                code,
                "Looks like you're offline. Please reconnect and refresh to continue"
            )
            is NetworkErrors.RequestTimedOut -> ServerError(
                code,
                "Looks like you're offline. Please reconnect and refresh to continue"
            )
            is NetworkErrors.BadGateway -> ServerError(code, "Bad Gateway")
            is NetworkErrors.NotFound -> ServerError(code, "Resource Not Found")
            is NetworkErrors.Forbidden -> ServerError(
                code,
                "You don't have access to this information"
            )
            is NetworkErrors.InternalServerError -> getError(error.response)
            is NetworkErrors.UnknownError -> ServerError(code, getDefaultMessage())
        }
    }

    private fun getDefaultMessage(): String {
        return "Something went wrong."
    }

    private fun getDefaultCode(): Int {
        return 0
    }


    data class ServerError(val code: Int?, val message: String?)

    sealed class NetworkErrors {
        object NoInternet : NetworkErrors()
        object RequestTimedOut : NetworkErrors()
        object BadGateway : NetworkErrors()
        object NotFound : NetworkErrors()
        object Forbidden : NetworkErrors()
        class InternalServerError(val response: String?) : NetworkErrors()
        open class UnknownError : NetworkErrors()
    }
}