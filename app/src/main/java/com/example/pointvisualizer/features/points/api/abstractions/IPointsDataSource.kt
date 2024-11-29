package com.example.pointvisualizer.features.points.api.abstractions

import com.example.pointvisualizer.features.points.api.models.PointResponseDto

interface IPointsDataSource {
    suspend fun getPoints(count: Int): PointResponseDto
}