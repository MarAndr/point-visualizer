package com.example.pointvisualizer.features.core.loading

sealed interface LoadingState<out T> {
    data object Idle : LoadingState<Nothing>
    data object Loading : LoadingState<Nothing>
    data class Error(val e: Exception) : LoadingState<Nothing>
    data class Data<T>(val t: T) : LoadingState<T>
}