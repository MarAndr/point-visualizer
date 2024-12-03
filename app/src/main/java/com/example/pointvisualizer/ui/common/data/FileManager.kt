package com.example.pointvisualizer.ui.common.data

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri

class FileManager(private val context: Context) : IFileManager {
    override fun saveBitmapToUri(uri: Uri, bitmap: Bitmap): Result<Unit> {
        return try {
            val outputStream = context.contentResolver.openOutputStream(uri)
                ?: return Result.failure(IllegalStateException("Unable to open output stream for the given URI: $uri"))
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}