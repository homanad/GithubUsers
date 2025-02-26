package com.homanad.android.githubusers.ui.screens.details.vm

import com.homanad.android.data.repositories.GithubRepositoryImpl
import com.homanad.android.domain.common.RequestState
import com.homanad.android.domain.repositories.GithubRepository
import com.homanad.android.domain.usecases.github.GetGithubUserUseCase
import com.homanad.android.githubusers.ui.screens.util.FakeLocalDataSourceImpl
import com.homanad.android.githubusers.ui.screens.util.FakeRemoteDataSourceImpl
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
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
class DetailsViewModelTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private lateinit var githubRepository: GithubRepository
    private lateinit var getGithubUserUseCase: GetGithubUserUseCase
    private lateinit var localDataSourceImpl: FakeLocalDataSourceImpl
    private lateinit var remoteDataSourceImpl: FakeRemoteDataSourceImpl

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        localDataSourceImpl = FakeLocalDataSourceImpl()
        remoteDataSourceImpl = FakeRemoteDataSourceImpl()
        githubRepository = GithubRepositoryImpl(localDataSourceImpl, remoteDataSourceImpl)
        getGithubUserUseCase = GetGithubUserUseCase(githubRepository)
    }

    @Test
    fun shouldReturnRemoteUser() = runTest {
        val index = Random.nextLong(1, 140)
        val username = "remote$index"
        val userState = getGithubUserUseCase(username).last()

        assert(userState is RequestState.Data && userState.data?.username == username)
    }

    @Test
    fun shouldReturnLocalUser() = runTest {
        val index = Random.nextLong(1, 20)
        val username = "local$index"
        val userState = getGithubUserUseCase(username).last()

        assert(userState is RequestState.Data && userState.data?.username == username)
    }

    @Test(expected = NullPointerException::class)
    fun shouldReturnNullPointerException() = runTest {
        val username = "notfound"
        getGithubUserUseCase(username).last()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }
}