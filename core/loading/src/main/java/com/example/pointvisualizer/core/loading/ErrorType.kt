package com.example.pointvisualizer.core.loading

sealed class ErrorType {
    data object Network : ErrorType()
    data class Server(val message: String?) : ErrorType()
    data class Unexpected(val e: Exception) : ErrorType()
}