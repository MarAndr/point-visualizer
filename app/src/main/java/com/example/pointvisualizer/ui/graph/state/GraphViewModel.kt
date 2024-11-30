package com.example.pointvisualizer.ui.graph.state

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.pointvisualizer.features.points.entities.PointsList
import com.example.pointvisualizer.ui.enterpoints.EnterPointsFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class GraphViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // todo завести navigation args
    private val savedPoints = savedStateHandle.get<PointsList>(EnterPointsFragment.POINTS_ARGS)!!
    val points = flowOf(
        GraphScreenState(
            points = savedPoints.points.sortedBy { it.x },
        )
    )
}