package com.example.pointvisualizer.features.points.impl.di

import com.example.pointvisualizer.features.points.api.IPointsDataRepository
import com.example.pointvisualizer.features.points.impl.PointsDataRepository
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