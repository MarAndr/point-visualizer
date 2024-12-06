package com.example.pointvisualizer.features.points.api.entities

import kotlinx.serialization.Serializable

@Serializable
data class PointsList(
    val points: List<Point>
)

@Serializable
data class Point(
    val x: Float,
    val y: Float,
)
