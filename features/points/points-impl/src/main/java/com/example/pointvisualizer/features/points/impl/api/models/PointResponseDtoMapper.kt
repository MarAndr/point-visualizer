package com.example.pointvisualizer.features.points.impl.api.models

import com.example.pointvisualizer.features.points.api.entities.Point
import com.example.pointvisualizer.features.points.api.entities.PointsList
import javax.inject.Inject

class PointResponseDtoMapper @Inject constructor() {
    internal fun map(dto: PointResponseDto): PointsList {
        return PointsList(
            points = dto.points.map {
                Point(
                    x = it.x,
                    y = it.y,
                )
            }
        )
    }
}