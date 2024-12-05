package com.example.pointvisualizer.features.files.impl.di

import com.example.pointvisualizer.features.files.api.IFileManager
import com.example.pointvisualizer.features.files.impl.FileManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface FilesModule {

    @Binds
    @Singleton
    fun provideFileManager(
        repository: FileManager,
    ): IFileManager
}