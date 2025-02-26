package com.homanad.android.githubusers.common.base

/**
 * This class is the base class for UI data mappers
 * @param F Original data type
 * @param T Target data type
 */
abstract class BaseMapper<F, T> {
    abstract operator fun invoke(from: F): T
}