package com.example.pointvisualizer.ui.graph.state

import com.example.pointvisualizer.features.core.network.ErrorType

sealed interface GraphScreenEvent {
    data object FileSaveSuccess : GraphScreenEvent
    data class FileSaveFailure(val errorType: ErrorType) : GraphScreenEvent
}