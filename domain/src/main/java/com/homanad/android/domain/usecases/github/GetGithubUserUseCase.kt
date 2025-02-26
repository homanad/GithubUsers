package com.homanad.android.domain.usecases.github

import com.homanad.android.domain.common.RequestState
import com.homanad.android.domain.models.GithubUser
import com.homanad.android.domain.repositories.GithubRepository
import com.homanad.android.domain.usecases.BaseCallbackUseCaseWithParams
import kotlinx.coroutines.flow.Flow

/**
 * This use case is used to get user details.
 * @param githubRepository Repository to execute use case
 */
class GetGithubUserUseCase(
    private val githubRepository: GithubRepository
) : BaseCallbackUseCaseWithParams<String, RequestState<GithubUser>>() {

    //    override suspend fun execute(params: String): GithubUser {
//        return githubRepository.getUser(params)
//    }

    //    override suspend fun execute(params: String): Flow<GithubUser> {
//        return githubRepository.getUser(params)
//    }
    override suspend fun execute(params: String): Flow<RequestState<GithubUser>> {
        return githubRepository.getUser(params)
    }
}