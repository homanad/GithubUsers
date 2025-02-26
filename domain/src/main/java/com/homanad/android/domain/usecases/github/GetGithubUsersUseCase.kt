package com.homanad.android.domain.usecases.github

import com.homanad.android.domain.models.GithubUser
import com.homanad.android.domain.repositories.GithubRepository
import com.homanad.android.domain.usecases.BaseUseCaseWithParams

/**
 * This use case is used to get a list of user.
 * @param githubRepository Repository to execute use case
 * @see GetGithubUsersUseCase.Params Parameters for this usecase
 */
class GetGithubUsersUseCase(
    private val githubRepository: GithubRepository
) : BaseUseCaseWithParams<GetGithubUsersUseCase.Params, List<GithubUser>>() {

    class Params(
        val perPage: Int,
        val since: Int
    )

    override suspend fun execute(params: Params): List<GithubUser> {
        return githubRepository.getUsers(params.perPage, params.since)
    }
}