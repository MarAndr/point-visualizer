package com.example.pointvisualizer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pointvisualizer.IPointsDataRepository
import com.example.pointvisualizer.Point
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PointsViewModel @Inject constructor(
    private val repository: IPointsDataRepository,
) : ViewModel() {

    private var _points = MutableStateFlow<List<Point>>(emptyList())
    private val points: StateFlow<List<Point>> = _points

    fun getPoints(count: Int) {
        viewModelScope.launch {
            _points.value = repository.getPoints(count).points
        }
    }
}