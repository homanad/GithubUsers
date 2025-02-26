package com.homanad.android.githubusers.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

suspend inline fun <T> safeFlow(
    callFlow: Flow<T>,
    crossinline onLoading: () -> Unit = {},
    crossinline onError: (t: Throwable) -> Unit = {},
    crossinline onCompletion: () -> Unit = {},
    crossinline onResult: (collect: T) -> Unit = {}
) {
    callFlow
        .onStart { onLoading.invoke() }
        .catch { onError.invoke(it) }
        .onCompletion { onCompletion.invoke() }
        .collect { onResult.invoke(it) }
}