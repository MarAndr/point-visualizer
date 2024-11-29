package com.example.pointvisualizer.features.points.api.models

import com.google.gson.annotations.SerializedName


data class PointResponseDto(
    @SerializedName("points")
    val points: List<PointDto>
)

data class PointDto(
    val x: Double,
    val y: Double,
)
