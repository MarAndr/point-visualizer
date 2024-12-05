package com.example.pointvisualizer.features.points.impl.usercase

import com.example.pointvisualizer.core.loading.LoadingState
import com.example.pointvisualizer.features.points.api.usecase.IGetPointsUseCase
import com.example.pointvisualizer.features.points.entities.PointsList
import com.example.pointvisualizer.features.points.impl.PointsDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPointsUseCase @Inject constructor(
    private val repo: PointsDataRepository,
) : IGetPointsUseCase {
    override fun invoke(count: Int): Flow<LoadingState<PointsList>> {
        return repo.getPoints(count)
    }
}