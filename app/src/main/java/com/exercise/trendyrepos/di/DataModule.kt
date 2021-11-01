package com.exercise.trendyrepos.di

import com.exercise.trendyrepos.data.DataRepository
import com.exercise.trendyrepos.data.IDataInfo
import com.exercise.trendyrepos.data.local.ITrendyReposDatabase
import com.exercise.trendyrepos.data.local.LocalRepository
import com.exercise.trendyrepos.data.remote.RemoteRepository
import com.exercise.trendyrepos.data.remote.ReposApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun provideDataRepository(dataRepository: DataRepository): IDataInfo

    @Binds
    @Singleton
    abstract fun provideLocalRepository(localRepository: LocalRepository): ITrendyReposDatabase

    @Binds
    @Singleton
    abstract fun provideRemoteRepository(remoteRepository: RemoteRepository): ReposApi
}