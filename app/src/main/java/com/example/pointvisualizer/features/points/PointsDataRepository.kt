package com.example.pointvisualizer.features.points

import com.example.pointvisualizer.features.points.abstractions.IPointsDataRepository
import com.example.pointvisualizer.features.points.api.abstractions.IPointsDataSource
import com.example.pointvisualizer.features.points.entities.Point
import com.example.pointvisualizer.features.points.entities.PointsList
import javax.inject.Inject

class PointsDataRepository @Inject constructor(
    private val dataSource: IPointsDataSource,
): IPointsDataRepository {
    override suspend fun getPoints(count: Int): PointsList {
        val pointsDto = dataSource.getPoints(count).points
        return PointsList(
            points = pointsDto.map {
                Point(
                    x = it.x,
                    y = it.y,
                )
            }
        )
    }
}