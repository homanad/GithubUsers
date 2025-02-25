package com.homanad.android.domain.usecases

import kotlinx.coroutines.flow.Flow

abstract class BaseCallbackUseCaseWithParams<in Params, out ReturnType> {

    protected abstract suspend fun execute(params: Params): Flow<ReturnType>

    suspend operator fun invoke(params: Params) = execute(params)
}