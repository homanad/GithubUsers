package com.homanad.android.data.repositories

import com.homanad.android.data.database.dao.UserDao
import com.homanad.android.data.mappers.toGithubUser
import com.homanad.android.data.mappers.toUserEntity
import com.homanad.android.data.service.GithubService
import com.homanad.android.domain.models.GithubUser
import com.homanad.android.domain.repositories.GithubRepository

class GithubRepositoryImpl(
    private val userDao: UserDao,
    private val githubService: GithubService
) : GithubRepository {

    override suspend fun getUsers(perPage: Int, since: Int): List<GithubUser> {
        val local = userDao.getAll() //TODO handle paging for room/refactor logic
        return if (local.isEmpty()) {
            val remote = githubService.getUsers(perPage, since)
            userDao.insertUsers(remote.map { it.toUserEntity() })
            remote.map { it.toGithubUser() }
        } else {
            local.map { it.toGithubUser() }
        }
    }

    override suspend fun getUser(username: String): GithubUser {
        val local = userDao.getUserByUsername(username)
        return if (local == null) {
            val remote = githubService.getUser(username)
            userDao.insertUser(remote.toUserEntity())
            remote.toGithubUser()
        } else {
            local.toGithubUser()
        }
    }
}