package com.example.pointvisualizer.ui.graph.di

import android.content.Context
import com.example.pointvisualizer.ui.common.data.FileManager
import com.example.pointvisualizer.ui.common.data.IFileManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FileManagerModule {
    @Provides
    @Singleton
    fun provideFileManager(@ApplicationContext context: Context): IFileManager {
        return FileManager(context)
    }
}