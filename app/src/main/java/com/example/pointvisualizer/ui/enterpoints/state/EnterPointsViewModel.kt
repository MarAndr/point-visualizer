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

    companion object {
        private const val ENTERED_POINTS_COUNT_MIN = 1
        private const val ENTERED_POINTS_COUNT_MAX = 1000
    }

    private val enteredPointsState = MutableStateFlow("")
    private val pointsRequestState =
        MutableStateFlow<EnterPointsRequestState>(EnterPointsRequestState.Idle)

    val screenState = combine(
        enteredPointsState,
        pointsRequestState
    ) { enteredPoints, pointsRequest ->
        val enteredPointsInt = enteredPoints.toIntOrNull()
        EnterPointsScreenState(
            validInput = EnterPointsValidationState(
                isNotEmpty = enteredPoints.isNotBlank(),
                isLessThanMax = enteredPointsInt != null && enteredPointsInt <= ENTERED_POINTS_COUNT_MAX,
                isMoreThanMin =  enteredPointsInt != null && enteredPointsInt >= ENTERED_POINTS_COUNT_MIN,
            ),
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
}