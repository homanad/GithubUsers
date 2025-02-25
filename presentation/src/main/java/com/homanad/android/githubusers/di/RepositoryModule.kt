package com.homanad.android.githubusers.di

import com.homanad.android.data.repositories.GithubRepositoryImpl
import com.homanad.android.domain.repositories.GithubRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * This is the Hilt DI module class, provide implementation instances for repositories
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindGithubRepository(
        githubRepositoryImpl: GithubRepositoryImpl
    ): GithubRepository
}