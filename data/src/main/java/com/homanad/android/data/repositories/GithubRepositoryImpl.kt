package com.homanad.android.data.repositories

import com.homanad.android.data.Constants
import com.homanad.android.data.database.entities.UserEntity
import com.homanad.android.data.datasource.local.LocalGithubDataSource
import com.homanad.android.data.datasource.remote.RemoteGithubDataSource
import com.homanad.android.data.mappers.toGithubUser
import com.homanad.android.data.mappers.toUserEntity
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
 * @param localDataSource Local data source to access cached data
 * @param remoteDataSource Remote data source to access remote data
 */
class GithubRepositoryImpl @Inject constructor(
    private val localDataSource: LocalGithubDataSource,
    private val remoteDataSource: RemoteGithubDataSource
) : GithubRepository {

    private fun isFirstPage(since: Int) = since == 0

    private fun shouldRefreshUsers(isFirstPage: Boolean, localData: List<UserEntity>): Boolean {
        if (!isFirstPage || localData.isEmpty()) return true

        val currentMillis = System.currentTimeMillis()
        val lastMillis = localData.getOrNull(localData.lastIndex)?.updatedMillis ?: currentMillis

        return currentMillis - lastMillis >= Constants.VALID_CACHING_TIME
    }

    private suspend fun getRemoteUsers(perPage: Int, since: Int): List<RemoteUser> {
        return remoteDataSource.getUsers(perPage, since)
    }

    override suspend fun getUsers(perPage: Int, since: Int): List<GithubUser> {
        val localData = localDataSource.getUsers(perPage, since)
        val isFirstPage = isFirstPage(since)
        val shouldRefresh = shouldRefreshUsers(isFirstPage, localData)

        return if (shouldRefresh) {
            val remote = getRemoteUsers(perPage, since).also {
                if (isFirstPage) localDataSource.refreshCache(it.map { item -> item.toUserEntity() })
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
        val local = localDataSource.getUser(username)

        local?.let {
            emit(RequestState.Data(it.toGithubUser()))
        }

        if (shouldRefreshUser(local)) {
            emit(RequestState.Loading())

            val remoteData = remoteDataSource.getUser(username)
                .also { localDataSource.insertOrReplaceUser(it.toUserEntity()) }

            emit(RequestState.Data(remoteData.toGithubUser()))
        }
    }
}