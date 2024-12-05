package com.example.pointvisualizer.features.points.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class PointsList(
    val points: List<Point>
) : Parcelable

@Serializable
@Parcelize
data class Point(
    val x: Float,
    val y: Float,
) : Parcelable
