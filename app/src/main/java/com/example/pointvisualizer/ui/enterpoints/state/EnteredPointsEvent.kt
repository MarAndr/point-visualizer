package com.example.pointvisualizer.ui.enterpoints.state

interface EnteredPointsEvent {
    data class Error(val errorType: ErrorType): EnteredPointsEvent
}

sealed class ErrorType {
    data object Network : ErrorType()
    class Server(val message: String?) : ErrorType()
    data object Unexpected : ErrorType()
}