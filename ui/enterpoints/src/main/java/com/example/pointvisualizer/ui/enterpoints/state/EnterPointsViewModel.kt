package com.example.pointvisualizer.ui.enterpoints.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pointvisualizer.core.loading.LoadingState
import com.example.pointvisualizer.core.loading.launchable
import com.example.pointvisualizer.features.points.api.usecase.IGetPointsUseCase
import com.example.pointvisualizer.ui.common.navigation.AppNavigator
import com.example.pointvisualizer.ui.common.navigation.NavigationTarget
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EnterPointsViewModel @Inject constructor(
    private val getPointsUseCase: IGetPointsUseCase,
    private val appNavigator: AppNavigator,
) : ViewModel() {

    companion object {
        private const val ENTERED_POINTS_COUNT_MIN = 1
        private const val ENTERED_POINTS_COUNT_MAX = 1000
    }

    private val enteredPointsState = MutableStateFlow("")
    private val enteredPointsCount = enteredPointsState.mapNotNull { it.toIntOrNull() }
    private val pointsRequestLaunchable = launchable(enteredPointsCount) { count ->
        getPointsUseCase(count)
    }

    private val _enteredPointsEvent = MutableSharedFlow<EnteredPointsEvent>()
    val enteredPointsEvent: SharedFlow<EnteredPointsEvent> = _enteredPointsEvent

    val screenState = combine(
        enteredPointsState,
        pointsRequestLaunchable.flow,
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

    init {
        pointsRequestLaunchable.flow
            .distinctUntilChanged()
            .onEach {
                if (it is LoadingState.Data) {
                    appNavigator.navigateTo(NavigationTarget.GraphScreen(it.data))
                }
                if (it is LoadingState.Error) {
                    _enteredPointsEvent.emit(EnteredPointsEvent.Error(it.errorType))
                }
            }
            .launchIn(viewModelScope)
    }

    fun getPoints() = viewModelScope.launch {
        pointsRequestLaunchable.launch()
    }

    fun onCountTextChanged(countString: String) {
        enteredPointsState.value = countString
    }
}