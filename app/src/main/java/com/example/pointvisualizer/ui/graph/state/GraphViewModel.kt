package com.example.pointvisualizer.ui.graph.state

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.example.pointvisualizer.features.points.entities.PointsList
import com.example.pointvisualizer.ui.activity.PointsListNavType
import com.example.pointvisualizer.ui.navigation.GraphScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import kotlin.reflect.typeOf

@HiltViewModel
class GraphViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val savedPoints by lazy {
        val screen = savedStateHandle.toRoute<GraphScreen>(
            typeMap = mapOf(typeOf<PointsList>() to PointsListNavType),
        )
        val pointsList = screen.points
        pointsList.points
    }

    val points = flowOf(
        GraphScreenState(
            points = savedPoints, // todo sort
        )
    )
}