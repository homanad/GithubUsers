package com.homanad.android.githubusers.ui.home.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.homanad.android.domain.models.GithubUser
import com.homanad.android.domain.usecases.github.GetGithubUsersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class UserPagingSource(
    private val getGithubUsersUseCase: GetGithubUsersUseCase
) : PagingSource<Int, GithubUser>() {

    companion object {
        private const val ITEM_PER_PAGE = 20
        private const val START_PAGE = 1
    }

    override fun getRefreshKey(state: PagingState<Int, GithubUser>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubUser> {
        val page = params.key ?: START_PAGE
        val since = ITEM_PER_PAGE * (page - 1)
        return try {
            val response = withContext(Dispatchers.IO) {
                getGithubUsersUseCase(
                    GetGithubUsersUseCase.Params(
                        ITEM_PER_PAGE,
                        since
                    )
                )
            }
            val nextKey = if (response.isEmpty()) {
                null
            } else {
                page + (params.loadSize / ITEM_PER_PAGE)
            }
            LoadResult.Page(
                data = response,
                prevKey = if (page == START_PAGE) null else page - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}