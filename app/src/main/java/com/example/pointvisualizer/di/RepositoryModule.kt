package com.example.pointvisualizer.di

import com.example.pointvisualizer.features.points.PointsDataRepository
import com.example.pointvisualizer.features.points.abstractions.IPointsDataRepository
import com.example.pointvisualizer.features.points.api.abstractions.IPointsDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePointsRepository(
        dataSource: IPointsDataSource,
    ): IPointsDataRepository {
        return PointsDataRepository(dataSource)
    }
}