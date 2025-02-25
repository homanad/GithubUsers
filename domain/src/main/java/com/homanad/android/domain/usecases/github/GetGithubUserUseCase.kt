package com.homanad.android.domain.usecases.github

import com.homanad.android.domain.models.GithubUser
import com.homanad.android.domain.repositories.GithubRepository
import com.homanad.android.domain.usecases.BaseCallbackUseCaseWithParams
import kotlinx.coroutines.flow.Flow

class GetGithubUserUseCase(
    private val githubRepository: GithubRepository
) : BaseCallbackUseCaseWithParams<String, GithubUser>() {

    //    override suspend fun execute(params: String): GithubUser {
//        return githubRepository.getUser(params)
//    }
    override suspend fun execute(params: String): Flow<GithubUser> {
        return githubRepository.getUser(params)
    }
}