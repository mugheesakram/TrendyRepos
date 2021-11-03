package com.exercise.trendyrepos.data

import com.exercise.trendyrepos.Utils
import com.exercise.trendyrepos.data.base.ApiResponse
import com.exercise.trendyrepos.data.dto.GithubRepos
import com.exercise.trendyrepos.data.local.ITrendyReposDatabase
import com.exercise.trendyrepos.data.remote.ReposApi
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class DataRepositoryTest {

    private val repository: ReposApi = mockk()
    private val localRepository: ITrendyReposDatabase = mockk()
    lateinit var sut: DataRepository

    @Before
    fun setUp() {
        sut = DataRepository(localRepository, repository)
    }

    @Test
    fun abc() {
        val res = getMockResponse()
        coEvery { localRepository.getTopRepos() } returns res.repos!!
        coEvery { repository.getTopApi("abc") } returns ApiResponse.Success(200, res)
        runBlocking {
            sut.getTopGithubRepositories("abc", false)
        }
    }

    private fun getMockResponse(): GithubRepos {
        val gson = GsonBuilder().create()
        val itemType = object : TypeToken<GithubRepos>() {}.type
        return gson.fromJson(Utils.readFileFromTestResources("success_response_api.json"), itemType)
    }

    @After
    fun tearDown() {
    }
}