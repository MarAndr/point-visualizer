package com.example.pointvisualizer.features.points.impl.api

import com.example.pointvisualizer.core.network.statefulApiCall
import com.example.pointvisualizer.features.points.impl.api.abstractions.IPointsDataSource
import com.example.pointvisualizer.features.points.impl.api.retrofit.PointsApi
import javax.inject.Inject

class PointsDataSource @Inject constructor(
    private val api: PointsApi,
) : IPointsDataSource {
    override fun getPoints(count: Int) = statefulApiCall {
        api.getPoints(count)
    }
}