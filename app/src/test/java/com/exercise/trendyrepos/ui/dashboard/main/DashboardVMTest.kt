package com.exercise.trendyrepos.ui.dashboard.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.exercise.trendyrepos.CoroutineRule
import com.exercise.trendyrepos.Utils
import com.exercise.trendyrepos.data.IDataInfo
import com.exercise.trendyrepos.data.MockDataRepository
import com.exercise.trendyrepos.data.base.ApiResponse
import com.exercise.trendyrepos.data.base.error.ApiError
import com.exercise.trendyrepos.data.dto.GithubRepos
import com.exercise.trendyrepos.getOrAwaitValue
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DashboardVMTest {

    @get:Rule
    var mainCoroutineRule = CoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var sut: DashboardVM
    private val mockDataRepository: IDataInfo = MockDataRepository()
    private val dataRepository: IDataInfo = mockk()


    @Test
    fun `get github repos if data is not null or empty`() = runBlocking {
        sut = DashboardVM(mockDataRepository)
        sut.getTopGithubRepos("language=+sort:stars", false)
        Assert.assertEquals(false, sut.repos.getOrAwaitValue().isNullOrEmpty())
    }

    @Test
    fun `get github repos from MockDataRepository if data is null or empty`() =
        runBlocking {
            sut = DashboardVM(mockDataRepository)

            sut.getTopGithubRepos("", false)
            val actual = sut.repos.getOrAwaitValue().isNullOrEmpty()

            Assert.assertEquals(true, actual)
        }

    @Test
    fun `get response when data is mocked and returns data successfully`() = runBlocking {
        sut = DashboardVM(dataRepository)
        val response = ApiResponse.Success(200, getMockResponse())
        coEvery { dataRepository.getTopGithubRepositories("abcdefg", false) } returns response

        sut.getTopGithubRepos("abcdefg", false)

        val actual = sut.repos.getOrAwaitValue().isNullOrEmpty()

        Assert.assertEquals(false, actual)
    }

    @Test
    fun `get empty repos list when VM APi method is returns error`() = runBlocking {
        sut = DashboardVM(dataRepository)
        val response = ApiResponse.Error(ApiError(504, "No Internet"))
        coEvery { dataRepository.getTopGithubRepositories("abcdefg", false) } returns response

        sut.getTopGithubRepos("abcdefg", false)

        Assert.assertEquals(true, sut.repos.getOrAwaitValue().isNullOrEmpty())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() //reset
    }

    private fun getMockResponse(): GithubRepos {
        val gson = GsonBuilder().create()
        val itemType = object : TypeToken<GithubRepos>() {}.type
        return gson.fromJson(Utils.readFileFromTestResources("success_response_api.json"), itemType)
    }
}