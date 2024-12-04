package com.example.pointvisualizer.features.core.loading

import com.example.pointvisualizer.features.core.network.ErrorType

sealed interface LoadingState<out T> {
    data object Idle : LoadingState<Nothing>
    data object Loading : LoadingState<Nothing>
    data class Error(val errorType: ErrorType) : LoadingState<Nothing>
    data class Data<T>(val data: T) : LoadingState<T>
}

fun <T, S> LoadingState<T>.map(mapper: (T) -> S): LoadingState<S> = when(this) {
    is LoadingState.Data -> LoadingState.Data(data = mapper(data))
    is LoadingState.Error ->LoadingState.Error(errorType)
    LoadingState.Idle -> LoadingState.Idle
    LoadingState.Loading -> LoadingState.Loading
}