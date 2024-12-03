package com.example.pointvisualizer.ui.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun showSnackbar(view: View, message: String, duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(view, message, duration).show()
}