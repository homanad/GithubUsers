package com.homanad.android.githubusers.ui.screens.util

import com.homanad.android.data.database.entities.UserEntity
import com.homanad.android.data.datasource.local.LocalGithubDataSource
import java.util.UUID

class FakeLocalDataSourceImpl : LocalGithubDataSource {

    private val dummyUsers: MutableList<UserEntity>
        get() {
            val username = UUID.randomUUID().toString()
            return (0..20L).map { createDummyUser(it, username) }.toMutableList()
        }

    private fun createDummyUser(id: Long, username: String) =
        UserEntity(id, username, "", "", "", 0, 0, "", System.currentTimeMillis()) //fake current time to avoid refresh cache data call

    override suspend fun getUsers(perPage: Int, since: Int): List<UserEntity> {
        println("---------since: $since, perPage: $perPage, to: ${since + perPage}")
        //Only cache the first page (20 items)
        val data = dummyUsers.subList(0, 0 + perPage)
        println("---------size: ${data.size}")
        return data
    }

    override suspend fun getUser(username: String): UserEntity? {
        return dummyUsers.find { it.username == username }
    }

    override suspend fun updateUser(user: UserEntity) {
        val tobeUpdated = dummyUsers.find { it.username == user.username }
        tobeUpdated?.let {
            dummyUsers.remove(it)
            dummyUsers.add(user)
        }
    }

    override suspend fun refreshCache(users: List<UserEntity>) {
        dummyUsers.clear()
        dummyUsers.addAll(users)
    }
}