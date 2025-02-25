package com.homanad.android.domain.usecases

/**
 * This class is the base class for use cases that receive data once
 */
abstract class BaseUseCaseWithParams<in Params, out ReturnType> {

    /**
     * This function will be called when the use case is invoked
     * @param params Defines the params passed in
     * @return Return data ReturnType
     */
    protected abstract suspend fun execute(params: Params): ReturnType

    suspend operator fun invoke(params: Params) = execute(params)
}