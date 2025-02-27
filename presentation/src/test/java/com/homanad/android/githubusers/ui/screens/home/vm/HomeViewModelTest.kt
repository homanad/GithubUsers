package com.homanad.android.githubusers.ui.screens.home.vm

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.testing.asSnapshot
import com.homanad.android.data.repositories.GithubRepositoryImpl
import com.homanad.android.domain.repositories.GithubRepository
import com.homanad.android.domain.usecases.github.GetGithubUsersUseCase
import com.homanad.android.githubusers.common.Constants.USER_ITEMS_PER_PAGE
import com.homanad.android.githubusers.mappers.UserItemMapper
import com.homanad.android.githubusers.util.FakeLocalDataSourceImpl
import com.homanad.android.githubusers.util.FakeRemoteDataSourceImpl
import com.homanad.android.githubusers.ui.screens.home.paging.UserPagingSource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

@ExperimentalCoroutinesApi
@DelicateCoroutinesApi
class HomeViewModelTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private lateinit var pagingSource: UserPagingSource
    private lateinit var viewModel: FakeHomeViewModel
    private lateinit var githubRepository: GithubRepository
    private lateinit var getGithubUsersUseCase: GetGithubUsersUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        githubRepository =
            GithubRepositoryImpl(FakeLocalDataSourceImpl(), FakeRemoteDataSourceImpl())
        getGithubUsersUseCase = GetGithubUsersUseCase(githubRepository)
        pagingSource = UserPagingSource(getGithubUsersUseCase, UserItemMapper())
        viewModel = FakeHomeViewModel(pagingSource)
    }

    @Test
    fun shouldReturnUsers() = runTest {
        val snapshot = viewModel.userPagingFlow.asSnapshot()
        assert(snapshot.isNotEmpty())
    }

    @Test
    fun shouldLoadMoreItems() = runTest {
        val snapshot = viewModel.userPagingFlow.asSnapshot()
        val listSizeBefore = snapshot.size

        val newSnapshot = viewModel.userPagingFlow.asSnapshot {
            scrollTo(index = USER_ITEMS_PER_PAGE)
        }

        assert(listSizeBefore + USER_ITEMS_PER_PAGE == newSnapshot.size)
    }

    @Test
    fun shouldReturnCorrespondingNumberOfItems() = runTest {
        val randomPage = Random.nextInt(0, 5)
        val itemsPerPage = 10
        val users = getGithubUsersUseCase(GetGithubUsersUseCase.Params(itemsPerPage, randomPage))
        assert(users.size == itemsPerPage)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }
}

class FakeHomeViewModel(userPagingSource: UserPagingSource) {

    val userPagingFlow = Pager(
        config = PagingConfig(
            pageSize = USER_ITEMS_PER_PAGE,
            enablePlaceholders = false,
            initialLoadSize = USER_ITEMS_PER_PAGE
        ),
        pagingSourceFactory = { userPagingSource }
    ).flow
}