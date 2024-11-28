package com.example.pointvisualizer.di

import com.example.pointvisualizer.IPointsDataSource
import com.example.pointvisualizer.PointsApi
import com.example.pointvisualizer.PointsDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: PointsApi): IPointsDataSource {
        return PointsDataSource(apiService)
    }
}
