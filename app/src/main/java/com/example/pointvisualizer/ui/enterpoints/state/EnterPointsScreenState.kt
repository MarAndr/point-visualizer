package com.example.pointvisualizer.ui.enterpoints.state

import com.example.pointvisualizer.features.points.entities.PointsList

data class EnterPointsScreenState(
    val validInput: EnterPointsValidationState,
    val enterPointsState: EnterPointsRequestState,
)

sealed interface EnterPointsRequestState {
    data object Idle : EnterPointsRequestState

    data object Loading : EnterPointsRequestState

    data class Data(
        val points: PointsList,
    ) : EnterPointsRequestState
}

data class EnterPointsValidationState(
    val isNotEmpty: Boolean,
    val isMoreThanMin: Boolean,
    val isLessThanMax: Boolean,
) {
    val isValid = isNotEmpty && isMoreThanMin && isLessThanMax
}

