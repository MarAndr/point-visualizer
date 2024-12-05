package com.example.pointvisualizer.features.points.api.usecase

import com.example.pointvisualizer.core.loading.LoadingState
import com.example.pointvisualizer.features.points.entities.PointsList
import kotlinx.coroutines.flow.Flow

interface IGetPointsUseCase {
    operator fun invoke(count: Int): Flow<LoadingState<PointsList>>
}