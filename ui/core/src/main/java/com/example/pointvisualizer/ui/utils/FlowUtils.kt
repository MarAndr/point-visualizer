package com.example.pointvisualizer.ui.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withStarted
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> Flow<T>.collectWithStarted(
    lifecycleOwner: LifecycleOwner,
    collector: (T) -> Unit
) {
    lifecycleOwner.lifecycleScope.launch {
        this@collectWithStarted.collect { value ->
            lifecycleOwner.lifecycle.withStarted {
                collector(value)
            }
        }
    }
}