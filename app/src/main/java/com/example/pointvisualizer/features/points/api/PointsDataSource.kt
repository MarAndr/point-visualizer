package com.example.pointvisualizer.features.points.api

import com.example.pointvisualizer.features.core.network.statefulApiCall
import com.example.pointvisualizer.features.points.api.abstractions.IPointsDataSource
import com.example.pointvisualizer.features.points.api.retrofit.PointsApi
import javax.inject.Inject

class PointsDataSource @Inject constructor(
    private val api: PointsApi,
) : IPointsDataSource {
    override fun getPoints(count: Int) = statefulApiCall {
        api.getPoints(count)
    }
}