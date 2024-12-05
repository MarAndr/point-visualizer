package com.example.pointvisualizer.features.points.impl.api.models

import com.google.gson.annotations.SerializedName


data class PointResponseDto(
    @SerializedName("points")
    val points: List<PointDto>
)

data class PointDto(
    val x: Float,
    val y: Float,
)
