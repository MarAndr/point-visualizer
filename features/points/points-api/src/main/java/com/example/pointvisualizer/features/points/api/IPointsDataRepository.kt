package com.example.pointvisualizer.features.points.api

import com.example.pointvisualizer.core.loading.LoadingState
import com.example.pointvisualizer.features.points.entities.PointsList
import kotlinx.coroutines.flow.Flow

interface IPointsDataRepository {
    fun getPoints(count: Int): Flow<LoadingState<PointsList>>
}