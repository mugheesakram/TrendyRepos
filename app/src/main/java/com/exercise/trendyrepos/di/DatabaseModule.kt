package com.exercise.trendyrepos.di

import android.content.Context
import androidx.room.Room
import com.exercise.trendyrepos.data.local.AppDB
import com.exercise.trendyrepos.data.local.TrendyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideTrendyDao(appDatabase: AppDB): TrendyDao {
        return appDatabase.TrendyDao()
    }

    @Provides
    @Singleton
    fun provideAppDB(@ApplicationContext appContext: Context): AppDB {
        return Room.databaseBuilder(
            appContext,
            AppDB::class.java,
            "Trendy"
        ).build()
    }

}