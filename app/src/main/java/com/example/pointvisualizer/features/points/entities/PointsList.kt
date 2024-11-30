package com.example.pointvisualizer.features.points.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PointsList(
    val points: List<Point>
) : Parcelable

@Parcelize
data class Point(
    val x: Float,
    val y: Float,
) : Parcelable
