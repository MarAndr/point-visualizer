package com.example.pointvisualizer.ui.common.data

import android.graphics.Bitmap
import android.net.Uri

interface IFileManager {
    fun saveBitmapToUri(uri: Uri, bitmap: Bitmap): Result<Unit>
}