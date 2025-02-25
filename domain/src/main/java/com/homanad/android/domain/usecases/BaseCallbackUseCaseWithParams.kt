package com.homanad.android.domain.usecases

import kotlinx.coroutines.flow.Flow

/**
 * This class is the base class for use cases that need to listen to data from the flow
 */
abstract class BaseCallbackUseCaseWithParams<in Params, out ReturnType> {

    /**
     * This function will be called when the use case is invoked
     * @param params Defines the params passed in
     * @return Return data Flow<ReturnType>
     */
    protected abstract suspend fun execute(params: Params): Flow<ReturnType>

    suspend operator fun invoke(params: Params) = execute(params)
}