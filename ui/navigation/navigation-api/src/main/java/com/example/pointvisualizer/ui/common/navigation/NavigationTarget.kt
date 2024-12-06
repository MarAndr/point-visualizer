package com.example.pointvisualizer.ui.common.navigation

import com.example.pointvisualizer.features.points.api.entities.PointsList
import kotlinx.serialization.Serializable

sealed interface NavigationTarget {

    @Serializable
    data class GraphScreen(
        val points: PointsList,
    ) : NavigationTarget

    @Serializable
    data object EnterPointsScreen : NavigationTarget
}