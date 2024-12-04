package com.example.pointvisualizer.features.points

import com.example.pointvisualizer.features.core.loading.map
import com.example.pointvisualizer.features.points.abstractions.IPointsDataRepository
import com.example.pointvisualizer.features.points.api.abstractions.IPointsDataSource
import com.example.pointvisualizer.features.points.entities.Point
import com.example.pointvisualizer.features.points.entities.PointsList
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PointsDataRepository @Inject constructor(
    private val dataSource: IPointsDataSource,
) : IPointsDataRepository {
    override fun getPoints(count: Int) = dataSource.getPoints(count)
        .map { loadingState ->
            loadingState.map { pointsDto ->
                PointsList(
                    points = pointsDto.points.map {
                        Point(
                            x = it.x,
                            y = it.y,
                        )
                    }
                )
            }
        }
}