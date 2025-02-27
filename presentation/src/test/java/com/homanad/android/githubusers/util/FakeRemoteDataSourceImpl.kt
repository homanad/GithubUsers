package com.homanad.android.githubusers.util

import com.homanad.android.data.datasource.remote.RemoteGithubDataSource
import com.homanad.android.data.service.models.RemoteUser
import java.util.UUID

class FakeRemoteDataSourceImpl : RemoteGithubDataSource {

    private val dummyUsers: List<RemoteUser>
        get() {
            val username = UUID.randomUUID().toString()
            return (1..140L).map { createDummyUser(it, "remote$it") }
        }

    private fun createDummyUser(id: Long, username: String) =
        RemoteUser(id, username, "", "", "", 0, 0, "")

    override suspend fun getUsers(perPage: Int, since: Int): List<RemoteUser> {
        return dummyUsers.subList(since, since + perPage)
    }

    override suspend fun getUser(username: String): RemoteUser {
        return dummyUsers.find { it.login == username }!!
    }
}