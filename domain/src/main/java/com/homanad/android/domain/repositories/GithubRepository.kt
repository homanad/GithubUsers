package com.homanad.android.domain.repositories

import com.homanad.android.domain.models.GithubUser

interface GithubRepository {
    suspend fun getUsers(parPage: Int, since: Int): List<GithubUser>
    suspend fun getUser(username: String): GithubUser
}