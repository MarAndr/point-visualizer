package com.example.pointvisualizer.features.points.abstractions

import com.example.pointvisualizer.features.points.entities.PointsList

interface IPointsDataRepository {
    suspend fun getPoints(count: Int): PointsList
}