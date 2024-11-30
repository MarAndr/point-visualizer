package com.example.pointvisualizer.ui.graph.state

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.pointvisualizer.features.points.entities.Point
import com.example.pointvisualizer.ui.enterpoints.EnterPointsFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class GraphViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _points = MutableStateFlow<GraphScreenState?>(null)
    val points: StateFlow<GraphScreenState?> = _points

    init {
        val savedPoints = savedStateHandle.get<ArrayList<Point>>(EnterPointsFragment.POINTS_ARGS)
        _points.value = GraphScreenState(points = savedPoints)
    }
}