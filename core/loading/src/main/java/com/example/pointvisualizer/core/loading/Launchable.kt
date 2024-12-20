package com.example.pointvisualizer.core.loading

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart

fun <T, P> launchable(
    paramsFlow: Flow<P>,
    action: (P) -> Flow<LoadingState<T>>
): Launchable<T> =
    LaunchableImpl(
        paramsFlow = paramsFlow,
        action = action,
    )

interface Launchable<T> {
    val flow: Flow<LoadingState<T>>

    suspend fun launch()
}

@OptIn(ExperimentalCoroutinesApi::class)
class LaunchableImpl<T, P>(
    paramsFlow: Flow<P>,
    action: (P) -> Flow<LoadingState<T>>,
) : Launchable<T> {
    private val launchTrigger = MutableSharedFlow<Unit>()

    override val flow = paramsFlow
        .flatMapLatest { params ->
            launchTrigger
                .flatMapLatest { action(params) }
        }
        .onStart { emit(LoadingState.Idle) }

    override suspend fun launch() {
        launchTrigger.emit(Unit)
    }
}

