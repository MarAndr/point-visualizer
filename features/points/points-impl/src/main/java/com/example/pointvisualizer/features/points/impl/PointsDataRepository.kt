package com.example.pointvisualizer.features.points.impl

import com.example.pointvisualizer.core.loading.map
import com.example.pointvisualizer.features.points.api.IPointsDataRepository
import com.example.pointvisualizer.features.points.entities.Point
import com.example.pointvisualizer.features.points.entities.PointsList
import com.example.pointvisualizer.features.points.impl.api.abstractions.IPointsDataSource
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PointsDataRepository @Inject constructor(
    private val dataSource: IPointsDataSource,
) : IPointsDataRepository {
    // todo mapping to datasource
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