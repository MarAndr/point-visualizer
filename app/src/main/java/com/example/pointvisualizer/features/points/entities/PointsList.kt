package com.example.pointvisualizer.features.points.entities

data class PointsList(
    val points: List<Point>
)

data class Point(
    val x: Double,
    val y: Double,
)
