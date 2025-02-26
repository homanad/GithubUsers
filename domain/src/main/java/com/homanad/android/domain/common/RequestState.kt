package com.homanad.android.domain.common

sealed interface RequestState<T> {
    data class Error<T : Any>(val throwable: Throwable) : RequestState<T>
    class Loading<T : Any> : RequestState<T>
    data class Data<T : Any>(val data: T) : RequestState<T>
}