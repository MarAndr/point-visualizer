package com.example.pointvisualizer.ui.common.data

import android.graphics.Bitmap
import android.net.Uri
import com.example.pointvisualizer.features.core.loading.LoadingState
import kotlinx.coroutines.flow.Flow

interface IFileManager {
    fun saveBitmapToUri(uri: Uri, bitmap: Bitmap): Flow<LoadingState<out Unit>>
}