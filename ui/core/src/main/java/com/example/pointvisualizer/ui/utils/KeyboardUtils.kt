package com.example.pointvisualizer.ui.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

fun hideKeyboard(activity: Activity) {
    val inputMethodManager =
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    val view = activity.currentFocus
    inputMethodManager?.hideSoftInputFromWindow(
        view?.windowToken,
        InputMethodManager.HIDE_NOT_ALWAYS
    )
}