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
        try {
            val outputStream = context.contentResolver.openOutputStream(uri)
            if (outputStream == null) {
                emit(
                    LoadingState.Error(ErrorType.Unexpected(IllegalStateException("Can't open $uri")))
                )
                return@flow
            }
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            emit(LoadingState.Data(Unit))
        } catch (e: Exception) {
            emit(LoadingState.Error(ErrorType.Unexpected(e)))
        }
    }
}