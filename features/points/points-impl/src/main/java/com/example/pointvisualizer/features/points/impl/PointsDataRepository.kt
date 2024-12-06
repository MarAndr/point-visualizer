package com.example.pointvisualizer.features.points.impl

import com.example.pointvisualizer.features.points.api.IPointsDataRepository
import com.example.pointvisualizer.features.points.impl.api.abstractions.IPointsDataSource
import javax.inject.Inject

class PointsDataRepository @Inject constructor(
    private val dataSource: IPointsDataSource,
) : IPointsDataRepository {
    override fun getPoints(count: Int) = dataSource.getPoints(count)
}