package com.example.pointvisualizer.ui.graph.state

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.pointvisualizer.core.loading.LoadingState
import com.example.pointvisualizer.core.loading.launchable
import com.example.pointvisualizer.features.files.api.IFileManager
import com.example.pointvisualizer.features.points.api.entities.PointsList
import com.example.pointvisualizer.ui.common.navigation.NavigationTarget
import com.example.pointvisualizer.ui.navtype.PointsListNavType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.typeOf

@HiltViewModel
class GraphViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fileManager: IFileManager,
) : ViewModel() {

    private val saveParams = MutableSharedFlow<Pair<Uri, Bitmap>>()
    private val savingFlow = launchable(saveParams) { (uri, bitmap) ->
        fileManager.saveBitmapToUri(uri, bitmap)
    }

    private val savedPoints by lazy {
        val screen = savedStateHandle.toRoute<NavigationTarget.GraphScreen>(
            typeMap = mapOf(typeOf<PointsList>() to PointsListNavType),
        )
        val pointsList = screen.points
        pointsList.points.sortedBy { it.x }
    }

    val screenEventFlow: Flow<GraphScreenEvent> = savingFlow.flow
        .flowOn(Dispatchers.IO)
        .mapNotNull {
            when (it) {
                is LoadingState.Data -> GraphScreenEvent.FileSaveSuccess
                is LoadingState.Error -> GraphScreenEvent.FileSaveFailure(it.errorType)

                LoadingState.Idle -> null
                LoadingState.Loading -> null
            }
        }

    val screenState = savingFlow.flow
        .map { savingState ->
            GraphScreenState(
                points = savedPoints,
                savingLoadingState = savingState,
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            initialValue = GraphScreenState(
                points = savedPoints,
                savingLoadingState = LoadingState.Idle,
            )
        )

    fun saveGraph(uri: Uri, bitmap: Bitmap) {
        viewModelScope.launch {
            saveParams.emit(uri to bitmap)
            savingFlow.launch()
        }
    }
}