package com.example.pointvisualizer.ui.graph.state

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.pointvisualizer.features.points.entities.PointsList
import com.example.pointvisualizer.ui.activity.PointsListNavType
import com.example.pointvisualizer.ui.common.data.IFileManager
import com.example.pointvisualizer.ui.common.navigation.NavigationTarget
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.typeOf

@HiltViewModel
class GraphViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fileManager: IFileManager,
) : ViewModel() {

    private val _graphEvent = MutableSharedFlow<GraphScreenEvent>()
    val graphEvent: SharedFlow<GraphScreenEvent> = _graphEvent

    private val savedPoints by lazy {
        val screen = savedStateHandle.toRoute<NavigationTarget.GraphScreen>(
            typeMap = mapOf(typeOf<PointsList>() to PointsListNavType),
        )
        val pointsList = screen.points
        pointsList.points.sortedBy { it.x }
    }

    val points = flowOf(
        GraphScreenState(
            points = savedPoints,
        )
    )

    fun saveGraph(uri: Uri, bitmap: Bitmap) {
        viewModelScope.launch {
            val result = fileManager.saveBitmapToUri(uri, bitmap)
            if (result.isSuccess) {
                _graphEvent.emit(GraphScreenEvent.FileSaveSuccess)
            } else {
                _graphEvent.emit(GraphScreenEvent.FileSaveFailure(result.exceptionOrNull()?.message ?: "Unknown Error"))
            }
        }
    }
}