package com.example.pointvisualizer.ui.enterpoints.state

import com.example.pointvisualizer.features.core.network.ErrorType

interface EnteredPointsEvent {
    data class Error(val errorType: ErrorType): EnteredPointsEvent
}
