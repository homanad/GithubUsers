package com.homanad.android.data.datasource.remote

import com.homanad.android.data.service.models.RemoteUser

interface RemoteGithubDataSource {
    suspend fun getUsers(perPage: Int, since: Int): List<RemoteUser>
    suspend fun getUser(username: String): RemoteUser
}