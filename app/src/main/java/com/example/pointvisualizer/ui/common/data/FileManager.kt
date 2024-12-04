package com.example.pointvisualizer.ui.common.data

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.example.pointvisualizer.features.core.loading.LoadingState
import com.example.pointvisualizer.features.core.network.ErrorType
import kotlinx.coroutines.flow.flow

class FileManager(private val context: Context) : IFileManager {
    override fun saveBitmapToUri(uri: Uri, bitmap: Bitmap) = flow {
        emit(LoadingState.Loading)
        try {
            val outputStream = context.contentResolver.openOutputStream(uri)
            if (outputStream == null) {
                emit(
                    LoadingState.Error(ErrorType.UnableToOpenFile(uri))
                )
                return@flow
            }
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            emit(LoadingState.Data(Unit))
        } catch (e: Exception) {
            emit(LoadingState.Error(ErrorType.UnableToSaveGraphToFile))
        }
    }
}