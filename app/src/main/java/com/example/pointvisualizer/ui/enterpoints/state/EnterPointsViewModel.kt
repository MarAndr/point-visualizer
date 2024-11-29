package com.example.pointvisualizer.ui.enterpoints.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pointvisualizer.features.points.abstractions.IPointsDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnterPointsViewModel @Inject constructor(
    private val repository: IPointsDataRepository,
) : ViewModel() {

    private val enteredPointsState = MutableStateFlow<Int?>(null)
    private val pointsRequestState =
        MutableStateFlow<EnterPointsRequestState>(EnterPointsRequestState.Loading)

    private val screenState = combine(
        enteredPointsState,
        pointsRequestState
    ) { enteredPoints, pointsRequest ->
        EnterPointsScreenState(
            enteredPoints,
            pointsRequest,
        )
    }

    fun getPoints(count: Int) {
        viewModelScope.launch {
            pointsRequestState.value = EnterPointsRequestState.Loading
            try {
                val points = repository.getPoints(count)
                pointsRequestState.value = EnterPointsRequestState.Data(
                    points = points
                )
            } catch (e: Exception) {
                pointsRequestState.value = EnterPointsRequestState.Error(e)
            }
        }
    }
}