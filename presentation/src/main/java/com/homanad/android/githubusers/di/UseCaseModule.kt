package com.homanad.android.githubusers.di

import com.homanad.android.domain.repositories.GithubRepository
import com.homanad.android.domain.usecases.github.GetGithubUserUseCase
import com.homanad.android.domain.usecases.github.GetGithubUsersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetGithubUsersUseCase(
        githubRepository: GithubRepository
    ) = GetGithubUsersUseCase(githubRepository)

    @Provides
    fun provideGetGithubUserUseCase(
        githubRepository: GithubRepository
    ) = GetGithubUserUseCase(githubRepository)
}