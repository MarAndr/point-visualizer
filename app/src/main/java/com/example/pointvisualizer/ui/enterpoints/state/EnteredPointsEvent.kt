package com.example.pointvisualizer.ui.enterpoints.state

import com.example.pointvisualizer.features.points.entities.PointsList

interface EnteredPointsEvent {
    data class NavigateToGraphFragment(val points: PointsList) : EnteredPointsEvent
    data class Error(val errorType: ErrorType): EnteredPointsEvent
}

sealed class ErrorType {
    data object Network : ErrorType()
    class Server(val message: String?) : ErrorType()
    data object Unexpected : ErrorType()
}