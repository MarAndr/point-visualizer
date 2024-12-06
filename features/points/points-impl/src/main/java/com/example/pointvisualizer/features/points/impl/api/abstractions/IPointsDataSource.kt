package com.example.pointvisualizer.features.points.impl.api.abstractions

import com.example.pointvisualizer.core.loading.LoadingState
import com.example.pointvisualizer.features.points.api.entities.PointsList
import kotlinx.coroutines.flow.Flow

interface IPointsDataSource {
    fun getPoints(count: Int): Flow<LoadingState<PointsList>>
}