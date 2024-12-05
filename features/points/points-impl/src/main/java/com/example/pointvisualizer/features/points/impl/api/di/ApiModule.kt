package com.example.pointvisualizer.features.points.impl.api.di

import com.example.pointvisualizer.features.points.impl.api.PointsDataSource
import com.example.pointvisualizer.features.points.impl.api.abstractions.IPointsDataSource
import com.example.pointvisualizer.features.points.impl.api.retrofit.PointsApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ApiModule {

    @Binds
    @Singleton
    fun provideRemoteDataSource(dataSource: PointsDataSource): IPointsDataSource

    companion object {
        @Provides
        @Singleton
        fun provideApiService(retrofit: Retrofit): PointsApi {
            return retrofit.create(PointsApi::class.java)
        }
    }
}
