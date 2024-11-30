package com.example.pointvisualizer.features.points.api

import com.example.pointvisualizer.features.points.api.abstractions.IPointsDataSource
import com.example.pointvisualizer.features.points.api.models.PointResponseDto
import com.example.pointvisualizer.features.points.api.retrofit.PointsApi
import javax.inject.Inject

class PointsDataSource @Inject constructor(
    private val api: PointsApi,
): IPointsDataSource {
    // todo convert to flow
    override suspend fun getPoints(count: Int): PointResponseDto {
        return api.getPoints(count)
    }
}