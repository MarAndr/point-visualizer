package com.example.pointvisualizer.ui.common.navigation

interface AppNavigator {
    suspend fun navigateTo(target: NavigationTarget)

    suspend fun popBackStack()
}