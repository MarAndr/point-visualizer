package com.example.pointvisualizer.features.core.network

import android.net.Uri
import com.example.pointvisualizer.features.core.loading.LoadingState
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

fun <T> statefulApiCall(
    apiCall: suspend () -> T,
) = flow<LoadingState<T>> {
    emit(LoadingState.Loading)

    try {
        val callResult = apiCall()
        emit(LoadingState.Data(callResult))
    } catch (e: HttpException) {
        val errorBody = e.response()?.errorBody()?.string()
        emit(LoadingState.Error(ErrorType.Server(errorBody)))
    } catch (e: IOException) {
        emit(LoadingState.Error(ErrorType.Network))
    } catch (e: Exception) {
        emit(LoadingState.Error(ErrorType.Unexpected))
    }
}.distinctUntilChanged()

sealed class ErrorType {
    data object Network : ErrorType()
    class Server(val message: String?) : ErrorType()
    data class UnableToOpenFile(val uri: Uri) : ErrorType()
    data object UnableToSaveGraphToFile : ErrorType()
    data object Unexpected : ErrorType()
}