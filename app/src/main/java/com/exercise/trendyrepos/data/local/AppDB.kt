package com.exercise.trendyrepos.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.exercise.trendyrepos.data.responses.Repo

@Database(entities = [Repo::class], version = 1, exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class AppDB : RoomDatabase() {
    abstract fun TrendyDao(): TrendyDao
}