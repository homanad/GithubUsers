package com.homanad.android.data.datasource.remote

import com.homanad.android.data.service.GithubService
import com.homanad.android.data.service.models.RemoteUser
import javax.inject.Inject

/**
 * This is the implementation class of RemoteGithubDataSource
 * @see RemoteGithubDataSource
 * @param githubService Retrofit service to access data from the server
 */
class RemoteGithubDataSourceImpl @Inject constructor(
    private val githubService: GithubService
) : RemoteGithubDataSource {

    override suspend fun getUsers(perPage: Int, since: Int): List<RemoteUser> {
        return githubService.getUsers(perPage, since)
    }

    override suspend fun getUser(username: String): RemoteUser {
        return githubService.getUser(username)
    }
}