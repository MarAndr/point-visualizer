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
internal class EnterPointsViewModel @Inject constructor(
    private val repository: IPointsDataRepository,
) : ViewModel() {

    private val enteredPointsState = MutableStateFlow("")
    private val pointsRequestState =
        MutableStateFlow<EnterPointsRequestState>(EnterPointsRequestState.Idle)

    val screenState = combine(
        enteredPointsState,
        pointsRequestState
    ) { enteredPoints, pointsRequest ->
        val enteredPointsInt = enteredPoints.toIntOrNull()
        EnterPointsScreenState(
            validInput = enteredPointsInt != null && enteredPointsInt in enteredPointsRange,
            enterPointsState = pointsRequest,
        )
    }

    fun getPoints(countString: String) {
        val count = countString.toIntOrNull() ?: return

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

    fun onCountTextChanged(countString: String) {
        enteredPointsState.value = countString
    }

    companion object {
        private val enteredPointsRange = 1..1000
    }
}