package com.exercise.trendyrepos.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.exercise.trendyrepos.data.responses.Repo

@Dao
interface TrendyDao {
    @Query("SELECT * FROM github_repos")
    suspend fun getAllTopRepos(): List<Repo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRepos(repos: List<Repo>)
}