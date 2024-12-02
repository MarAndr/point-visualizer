package com.example.pointvisualizer.ui.common.navigation

import kotlinx.coroutines.flow.Flow

interface NavigationEventProvider {
    val eventsFlow: Flow<NavigationEvent>
}

sealed interface NavigationEvent {
    data class NavigateTo(val target: NavigationTarget) : NavigationEvent
    data object PopBackStack : NavigationEvent
}