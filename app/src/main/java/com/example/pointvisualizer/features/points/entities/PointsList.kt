package com.example.pointvisualizer.features.points.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class PointsList(
    val points: List<Point>
)

@Parcelize
data class Point(
    val x: Double,
    val y: Double,
): Parcelable
