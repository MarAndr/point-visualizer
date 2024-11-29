package com.example.pointvisualizer.features.points.di

import com.example.pointvisualizer.features.points.PointsDataRepository
import com.example.pointvisualizer.features.points.abstractions.IPointsDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun providePointsRepository(
        repository: PointsDataRepository,
    ): IPointsDataRepository
}