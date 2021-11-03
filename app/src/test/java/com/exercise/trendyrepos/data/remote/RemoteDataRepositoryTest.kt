package com.exercise.trendyrepos.data.remote

import com.exercise.trendyrepos.Utils.enqueueResponse
import com.exercise.trendyrepos.Utils.readFileFromTestResources
import com.exercise.trendyrepos.data.base.ApiResponse
import com.exercise.trendyrepos.data.dto.GithubRepos
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
class RemoteDataRepositoryTest {
    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ReposService::class.java)

    private val sut = RemoteRepository(api)

    @Test
    fun `should fetch repos correctly given 200 response`() {
        mockWebServer.enqueueResponse("discover-movies-200.json", 200)
        val mock = readFileFromTestResources("success_response_api.json")
        mockWebServer.apply {
            enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(mock)
            )
        }
        runBlocking {

            val actual = sut.getTopApi("")

            val expected = ApiResponse.Success(
                200,
                getMockResponse("success_response_api.json")
            )

            assertEquals(expected, actual)
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    private fun getMockResponse(fileName: String): GithubRepos {
        val gson = GsonBuilder().create()
        val itemType = object : TypeToken<GithubRepos>() {}.type
        return gson.fromJson(readFileFromTestResources(fileName), itemType)
    }
}