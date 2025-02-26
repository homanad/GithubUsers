package com.homanad.android.domain.repositories

import com.homanad.android.domain.common.RequestState
import com.homanad.android.domain.models.GithubUser
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    suspend fun getUsers(perPage: Int, since: Int): List<GithubUser>
    suspend fun getUser(username: String): Flow<RequestState<GithubUser>>
}