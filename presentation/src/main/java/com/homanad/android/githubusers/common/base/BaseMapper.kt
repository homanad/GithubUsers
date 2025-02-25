package com.homanad.android.githubusers.common.base

abstract class BaseMapper<F, T> {

    abstract operator fun invoke(from: F): T
}