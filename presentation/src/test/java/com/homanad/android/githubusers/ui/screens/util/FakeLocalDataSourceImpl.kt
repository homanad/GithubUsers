package com.homanad.android.githubusers.ui.screens.util

import com.homanad.android.data.database.entities.UserEntity
import com.homanad.android.data.datasource.local.LocalGithubDataSource

class FakeLocalDataSourceImpl : LocalGithubDataSource {

    private val dummyUsers: MutableList<UserEntity>
        get() {
            return (1..20L).map { createDummyUser(it, "local$it") }.toMutableList()
        }

    private fun createDummyUser(id: Long, username: String) =
        UserEntity(
            id,
            username,
            "",
            "",
            "",
            0,
            0,
            "",
            System.currentTimeMillis()
        ) //fake current time to avoid refresh cache data call

    override suspend fun getUsers(perPage: Int, since: Int): List<UserEntity> {
        //Only cache the first page (20 items)
        return dummyUsers.subList(0, 0 + perPage)
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