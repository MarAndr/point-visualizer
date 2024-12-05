package com.example.pointvisualizer.ui.enterpoints.state

import com.example.pointvisualizer.core.loading.ErrorType

interface EnteredPointsEvent {
    data class Error(val errorType: ErrorType): EnteredPointsEvent
}
