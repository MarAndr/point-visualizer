package com.example.pointvisualizer.features.points.impl.api

import com.example.pointvisualizer.core.network.statefulApiCall
import com.example.pointvisualizer.features.points.impl.api.abstractions.IPointsDataSource
import com.example.pointvisualizer.features.points.impl.api.models.PointResponseDtoMapper
import com.example.pointvisualizer.features.points.impl.api.retrofit.PointsApi
import javax.inject.Inject

class PointsDataSource @Inject constructor(
    private val api: PointsApi,
    private val pointResponseDtoMapper: PointResponseDtoMapper,
) : IPointsDataSource {
    override fun getPoints(count: Int) = statefulApiCall {
        val dto = api.getPoints(count)
        return@statefulApiCall pointResponseDtoMapper.map(dto)
    }
}