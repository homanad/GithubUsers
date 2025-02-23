package com.homanad.android.domain.usecases.github

import com.homanad.android.domain.models.GithubUser
import com.homanad.android.domain.repositories.GithubRepository
import com.homanad.android.domain.usecases.BaseUseCaseWithParams

class GetGithubUserUseCase(
    private val githubRepository: GithubRepository
) : BaseUseCaseWithParams<String, GithubUser>() {

    override suspend fun execute(params: String): GithubUser {
        return githubRepository.getUser(params)
    }
}