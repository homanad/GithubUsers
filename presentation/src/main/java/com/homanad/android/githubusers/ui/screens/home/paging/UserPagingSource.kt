package com.homanad.android.githubusers.ui.screens.home.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.homanad.android.domain.usecases.github.GetGithubUsersUseCase
import com.homanad.android.githubusers.common.Constants.USER_ITEMS_PER_PAGE
import com.homanad.android.githubusers.mappers.UserItemMapper
import com.homanad.android.githubusers.models.UserItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserPagingSource @Inject constructor(
    private val getGithubUsersUseCase: GetGithubUsersUseCase,
    private val userItemMapper: UserItemMapper
) : PagingSource<Int, UserItem>() {

    companion object {
        private const val START_PAGE = 1
    }

    override fun getRefreshKey(state: PagingState<Int, UserItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserItem> {
        val page = params.key ?: START_PAGE
        val since = USER_ITEMS_PER_PAGE * (page - 1)
        return try {
            val response = withContext(Dispatchers.IO) {
                getGithubUsersUseCase(
                    GetGithubUsersUseCase.Params(
                        USER_ITEMS_PER_PAGE,
                        since
                    )
                ).map { userItemMapper(it) }
            }
            val nextKey = if (response.isEmpty()) {
                null
            } else {
                page + (params.loadSize / USER_ITEMS_PER_PAGE)
            }
            LoadResult.Page(
                data = response,
                prevKey = if (page == START_PAGE) null else page - 1,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}