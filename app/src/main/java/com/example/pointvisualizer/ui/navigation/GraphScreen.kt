package com.example.pointvisualizer.ui.navigation

import com.example.pointvisualizer.features.points.entities.PointsList
import kotlinx.serialization.Serializable

@Serializable
data class GraphScreen(
    val points: PointsList,
)
