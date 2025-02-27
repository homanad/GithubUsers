package com.homanad.android.data.datasource.local

import com.homanad.android.data.database.AppDatabase
import com.homanad.android.data.database.entities.UserEntity
import javax.inject.Inject

/**
 * This is the implementation class of LocalGithubDataSource
 * @see LocalGithubDataSource
 * @param appDatabase Database for accessing local data
 */
class LocalGithubDataSourceImpl @Inject constructor(
    appDatabase: AppDatabase
) : LocalGithubDataSource {

    private val userDao = appDatabase.userDao()

    override suspend fun getUsers(perPage: Int, since: Int): List<UserEntity> {
        return userDao.getUsersByPage(perPage, since)
    }

    override suspend fun getUser(username: String): UserEntity? {
        return userDao.getUserByUsername(username)
    }

    override suspend fun insertOrReplaceUser(user: UserEntity) {
        return userDao.insertOrReplaceUser(user)
    }

    override suspend fun refreshCache(users: List<UserEntity>) {
        return userDao.deleteAndInsert(users)
    }
}