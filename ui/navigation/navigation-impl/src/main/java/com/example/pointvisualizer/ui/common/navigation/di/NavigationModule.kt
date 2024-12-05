package com.example.pointvisualizer.ui.common.navigation.di

import com.example.pointvisualizer.ui.common.navigation.AppNavigator
import com.example.pointvisualizer.ui.common.navigation.AppNavigatorImpl
import com.example.pointvisualizer.ui.common.navigation.NavigationEventProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @Binds
    fun navEventsProvider(impl: AppNavigatorImpl): NavigationEventProvider

    @Binds
    fun appNavigator(impl: AppNavigatorImpl): AppNavigator
}