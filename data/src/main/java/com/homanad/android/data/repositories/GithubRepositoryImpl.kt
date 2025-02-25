package com.homanad.android.data.repositories

import com.homanad.android.data.database.dao.UserDao
import com.homanad.android.data.mappers.toGithubUser
import com.homanad.android.data.mappers.toUserEntity
import com.homanad.android.data.service.GithubService
import com.homanad.android.domain.models.GithubUser
import com.homanad.android.domain.repositories.GithubRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val githubService: GithubService
) : GithubRepository {

    override suspend fun getUsers(perPage: Int, since: Int): List<GithubUser> {
        println("--------since: $since")
        if (since == 0) {
            val localData = userDao.getAll()

            return if (localData.isEmpty()) {
                val remoteData = coroutineScope {
                    async {
                        println("------startRun: ${System.currentTimeMillis()}")
                        return@async try {
                            githubService.getUsers(perPage, since)
                                .also { userDao.deleteAndInsert(it.map { item -> item.toUserEntity() }) }
                        } catch (e: Exception) {
                            println("-------exception: $e")
                            emptyList()
                        }
                    }
                }
                println("------outside: ${System.currentTimeMillis()}")
                val data = remoteData.await().map { it.toGithubUser() }
                println("------endRun: ${System.currentTimeMillis()}")
                data
            } else {
                localData.map { it.toGithubUser() }
            }
        } else {
            println("--------getFromRemote")
            return githubService.getUsers(perPage, since).map { it.toGithubUser() }
        }
    }

    override suspend fun getUser(username: String): GithubUser {
//        val local = userDao.getUserByUsername(username)
//        return if (local == null) {
//            val remote = githubService.getUser(username)
//            userDao.insertUser(remote.toUserEntity())
//            remote.toGithubUser()
//        } else {
//            local.toGithubUser()
//        }
        return githubService.getUser(username).toGithubUser()
    }
}