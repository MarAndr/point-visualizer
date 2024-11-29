package com.example.pointvisualizer.di

import com.example.pointvisualizer.features.points.api.PointsDataSource
import com.example.pointvisualizer.features.points.api.abstractions.IPointsDataSource
import com.example.pointvisualizer.features.points.api.retrofit.PointsApi
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