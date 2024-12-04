package com.example.pointvisualizer.ui.enterpoints.state

import com.example.pointvisualizer.features.core.loading.LoadingState
import com.example.pointvisualizer.features.points.entities.PointsList

data class EnterPointsScreenState(
    val validInput: EnterPointsValidationState,
    val enterPointsState: LoadingState<PointsList>,
)

data class EnterPointsValidationState(
    val isNotEmpty: Boolean,
    val isMoreThanMin: Boolean,
    val isLessThanMax: Boolean,
) {
    val isValid = isNotEmpty && isMoreThanMin && isLessThanMax
}

