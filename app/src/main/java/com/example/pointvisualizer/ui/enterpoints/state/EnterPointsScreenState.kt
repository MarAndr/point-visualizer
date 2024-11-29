package com.example.pointvisualizer.ui.enterpoints.state

import com.example.pointvisualizer.features.points.entities.PointsList

data class EnterPointsScreenState(
    val points: Int?,
    val enterPointsState: EnterPointsRequestState,
)

sealed interface EnterPointsRequestState {
    data object Loading : EnterPointsRequestState

    data class Error(val e: Exception) : EnterPointsRequestState

    data class Data(
        val points: PointsList,
    ): EnterPointsRequestState
}