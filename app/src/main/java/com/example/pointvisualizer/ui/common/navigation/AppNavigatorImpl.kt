package com.example.pointvisualizer.ui.common.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNavigatorImpl @Inject constructor() : AppNavigator, NavigationEventProvider {

    override val eventsFlow = MutableSharedFlow<NavigationEvent>()

    override suspend fun navigateTo(target: NavigationTarget) {
        eventsFlow.emit(NavigationEvent.NavigateTo(target))
    }

    override suspend fun popBackStack() {
        eventsFlow.emit(NavigationEvent.PopBackStack)
    }
}