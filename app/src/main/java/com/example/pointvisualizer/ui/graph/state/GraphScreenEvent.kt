package com.example.pointvisualizer.ui.graph.state

sealed interface GraphScreenEvent {
    data object FileSaveSuccess : GraphScreenEvent
    data class FileSaveFailure(val error: String) : GraphScreenEvent
}