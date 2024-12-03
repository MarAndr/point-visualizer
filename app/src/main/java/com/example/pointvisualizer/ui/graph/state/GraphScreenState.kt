package com.example.pointvisualizer.ui.graph.state

import com.example.pointvisualizer.features.core.loading.LoadingState
import com.example.pointvisualizer.features.points.entities.Point

data class GraphScreenState(
    val points: List<Point>,
    val savingLoadingState: LoadingState<Unit>,
)
