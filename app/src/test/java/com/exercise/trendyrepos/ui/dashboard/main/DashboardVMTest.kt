package com.exercise.trendyrepos.ui.dashboard.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.exercise.trendyrepos.CoroutineRule
import com.exercise.trendyrepos.data.IDataInfo
import com.exercise.trendyrepos.data.MockDataRepository
import com.exercise.trendyrepos.getOrAwaitValue
import com.exercise.trendyrepos.utils.base.UIState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*

@ExperimentalCoroutinesApi
class DashboardVMTest {

    @get:Rule
    var mainCoroutineRule = CoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var sut: DashboardVM
    private val mockDataRepository: IDataInfo = MockDataRepository()

    @Before
    fun setUp() {
        sut = DashboardVM(mockDataRepository, DashboardState())
    }

    @Test
    fun `get github repos if data is not null or empty`() {
        sut.getTopGithubRepos("language=+sort:stars")
        Assert.assertEquals(false, sut.repos.getOrAwaitValue().isNullOrEmpty())
    }

    @Test
    fun `get github repos if data is null or empty`() {
        sut.getTopGithubRepos("")
        val actual = sut.repos.getOrAwaitValue().isNullOrEmpty()

        Assert.assertEquals(true, actual)
    }

    @After
    fun tearDown() {
    }
}