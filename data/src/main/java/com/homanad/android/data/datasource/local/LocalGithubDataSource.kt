package com.homanad.android.data.datasource.local

import com.homanad.android.data.database.entities.UserEntity

interface LocalGithubDataSource {
    suspend fun getUsers(perPage: Int, since: Int): List<UserEntity>
    suspend fun getUser(username: String): UserEntity?
    suspend fun insertOrReplaceUser(user: UserEntity)
    suspend fun refreshCache(users: List<UserEntity>)
}