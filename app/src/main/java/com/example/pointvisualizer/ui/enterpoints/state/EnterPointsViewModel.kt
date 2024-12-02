package com.example.pointvisualizer.ui.enterpoints.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pointvisualizer.features.points.abstractions.IPointsDataRepository
import com.example.pointvisualizer.ui.common.navigation.AppNavigator
import com.example.pointvisualizer.ui.common.navigation.NavigationTarget
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
internal class EnterPointsViewModel @Inject constructor(
    private val repository: IPointsDataRepository,
    private val appNavigator: AppNavigator,
) : ViewModel() {

    companion object {
        private const val ENTERED_POINTS_COUNT_MIN = 1
        private const val ENTERED_POINTS_COUNT_MAX = 1000
    }

    private val enteredPointsState = MutableStateFlow("")
    private val pointsRequestState =
        MutableStateFlow<EnterPointsRequestState>(EnterPointsRequestState.Idle)

    private val _enteredPointsEvent = MutableSharedFlow<EnteredPointsEvent>()
    val enteredPointsEvent: SharedFlow<EnteredPointsEvent> = _enteredPointsEvent

    val screenState = combine(
        enteredPointsState,
        pointsRequestState
    ) { enteredPoints, pointsRequest ->
        val enteredPointsInt = enteredPoints.toIntOrNull()
        EnterPointsScreenState(
            validInput = EnterPointsValidationState(
                isNotEmpty = enteredPoints.isNotBlank(),
                isLessThanMax = enteredPointsInt != null && enteredPointsInt <= ENTERED_POINTS_COUNT_MAX,
                isMoreThanMin = enteredPointsInt != null && enteredPointsInt >= ENTERED_POINTS_COUNT_MIN,
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
                appNavigator.navigateTo(NavigationTarget.GraphScreen(points))
            } catch (e: HttpException) {
                pointsRequestState.value = EnterPointsRequestState.Idle
                val errorBody = e.response()?.errorBody()?.string()
                _enteredPointsEvent.emit(
                    EnteredPointsEvent.Error(
                        ErrorType.Server(errorBody)
                    )
                )
            } catch (e: IOException) {
                pointsRequestState.value = EnterPointsRequestState.Idle
                _enteredPointsEvent.emit(
                    EnteredPointsEvent.Error(
                        ErrorType.Network
                    )
                )
            } catch (e: Exception) {
                pointsRequestState.value = EnterPointsRequestState.Idle
                _enteredPointsEvent.emit(
                    EnteredPointsEvent.Error(
                        ErrorType.Unexpected
                    )
                )
            }
        }
    }

    fun onCountTextChanged(countString: String) {
        enteredPointsState.value = countString
    }
}