package com.example.pointvisualizer.features.points.impl.api.models

import com.google.gson.annotations.SerializedName


data class PointResponseDto(
    @SerializedName("points")
    val points: List<PointDto>
)

data class PointDto(
    @SerializedName("x")
    val x: Float,
    @SerializedName("y")
    val y: Float,
)
