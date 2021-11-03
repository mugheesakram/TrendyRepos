package com.exercise.trendyrepos.di

import com.exercise.trendyrepos.data.base.RetroNetwork
import com.exercise.trendyrepos.data.remote.ReposService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun providesReposService(): ReposService =
        RetroNetwork().createService(ReposService::class.java)
}