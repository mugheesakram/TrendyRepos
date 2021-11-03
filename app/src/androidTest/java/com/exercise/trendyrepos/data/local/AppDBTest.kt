package com.exercise.trendyrepos.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.exercise.trendyrepos.base.Utils
import com.exercise.trendyrepos.data.dto.GithubRepos
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDBTest {

    private lateinit var appDB: AppDB
    private lateinit var trendyDao: TrendyDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDB = Room.inMemoryDatabaseBuilder(context, AppDB::class.java).build()
        trendyDao = appDB.TrendyDao()
    }

    @Test
    fun dataInsertionIsSuccessful() = runBlocking {
        val expected = getMockResponse("db_test.json")

        expected.repos?.let { trendyDao.insertAllRepos(it) }

        val actual = trendyDao.getAllTopRepos()
        Assert.assertEquals(expected.repos, actual)
    }


    @After
    fun tearDown() {
        appDB.close()
    }

    private fun getMockResponse(fileName: String): GithubRepos {
        val gson = GsonBuilder().create()
        val itemType = object : TypeToken<GithubRepos>() {}.type
        return gson.fromJson(Utils.readFileFromTestResources(fileName), itemType)
    }
}