package com.homanad.android.data.repositories

import com.homanad.android.data.Constants
import com.homanad.android.data.database.dao.UserDao
import com.homanad.android.data.database.entities.UserEntity
import com.homanad.android.data.mappers.toGithubUser
import com.homanad.android.data.mappers.toUserEntity
import com.homanad.android.data.service.GithubService
import com.homanad.android.data.service.models.RemoteUser
import com.homanad.android.domain.common.RequestState
import com.homanad.android.domain.models.GithubUser
import com.homanad.android.domain.repositories.GithubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * This is the implementation class of GithubRepository
 * @see GithubRepository
 * @param userDao Database DAO for accessing local data
 * @param githubService Retrofit service to access data from the server
 */
class GithubRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val githubService: GithubService
) : GithubRepository {

    private fun isFirstPage(since: Int) = since == 0

    private fun shouldRefreshUsers(isFirstPage: Boolean, localData: List<UserEntity>): Boolean {
        println("-------isFirstPage: $isFirstPage")
        println("-------isEmpty: ${localData.isEmpty()}")

        if (!isFirstPage || localData.isEmpty()) return true


        val currentMillis = System.currentTimeMillis()
        val lastMillis = localData.getOrNull(localData.lastIndex)?.updatedMillis ?: currentMillis
        println("-------currentMillis: $currentMillis")
        println("-------lastMillis: $lastMillis")

        return currentMillis - lastMillis >= Constants.VALID_CACHING_TIME
    }

    private suspend fun getRemoteUsers(perPage: Int, since: Int): List<RemoteUser> {
        println("------startRun: ${System.currentTimeMillis()}")
        val data = githubService.getUsers(perPage, since)
        return data
    }

    /**
     * //TODO
     */
    override suspend fun getUsers(perPage: Int, since: Int): List<GithubUser> {
        println("--------since: $since")
        val localData = userDao.getUsersByPage(perPage, since)
        val isFirstPage = isFirstPage(since)
        val should = shouldRefreshUsers(isFirstPage, localData)
        println("----------should: $should")

        return if (should) {
            val remote = getRemoteUsers(perPage, since).also {
                if (isFirstPage) userDao.deleteAndInsert(it.map { item -> item.toUserEntity() })
            }
            remote.map { it.toGithubUser() }
        } else {
            localData.map { it.toGithubUser() }
        }
    }

    private fun shouldRefreshUser(localData: UserEntity?): Boolean {
        return localData?.followers == null || localData.following == null
    }

    override suspend fun getUser(username: String): Flow<RequestState<GithubUser>> = flow {
        val local = userDao.getUserByUsername(username)

        local?.let {
            println("-------getFromLocal: $it")
            emit(RequestState.Data(it.toGithubUser()))
        }

        if (shouldRefreshUser(local)) {
            emit(RequestState.Loading())

            println("-------getFromRemote: $local")
            val remoteData = githubService.getUser(username)
                .also { userDao.updateUser(it.toUserEntity()) }

            emit(RequestState.Data(remoteData.toGithubUser()))
        }
    }
}