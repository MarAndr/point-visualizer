package com.example.pointvisualizer.features.files.impl

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.example.pointvisualizer.core.loading.ErrorType
import com.example.pointvisualizer.core.loading.LoadingState
import com.example.pointvisualizer.features.files.api.IFileManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FileManager @Inject constructor(
    @ApplicationContext private val context: Context,
) : IFileManager {

    override fun saveBitmapToUri(uri: Uri, bitmap: Bitmap) = flow {
        emit(LoadingState.Loading)
        val result = runCatching {
            context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            } ?: throw IllegalStateException("Can't open $uri")
        }
        emit(result.toLoadingState())
    }
}

private fun <T> Result<T>.toLoadingState(): LoadingState<Unit> {
    return fold(
        onSuccess = { LoadingState.Data(Unit) },
        onFailure = { LoadingState.Error(ErrorType.Unexpected(Exception(it))) }
    )
}