package com.example.pointvisualizer.features.files.api

import android.graphics.Bitmap
import android.net.Uri
import com.example.pointvisualizer.core.loading.LoadingState
import kotlinx.coroutines.flow.Flow

interface IFileManager {
    fun saveBitmapToUri(uri: Uri, bitmap: Bitmap): Flow<LoadingState<Unit>>
}