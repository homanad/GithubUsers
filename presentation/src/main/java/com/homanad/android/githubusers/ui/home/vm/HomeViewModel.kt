package com.homanad.android.githubusers.ui.home.vm

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.homanad.android.domain.models.GithubUser
import com.homanad.android.domain.usecases.github.GetGithubUsersUseCase
import com.homanad.android.githubusers.common.BaseViewModel
import com.homanad.android.githubusers.common.Constants.USER_ITEMS_PER_PAGE
import com.homanad.android.githubusers.ui.home.paging.UserPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGithubUsersUseCase: GetGithubUsersUseCase
) : BaseViewModel<HomeViewModel.Intent, HomeViewModel.State>() {

    sealed interface Intent {
        data object GetUsers : Intent
    }

    sealed interface State {
        data class UserList(val users: List<GithubUser>) : State
        data class UserList2(val users: PagingData<GithubUser>) : State
        data class Loading(val isLoading: Boolean) : State
    }

    override suspend fun processIntent(intent: Intent) {
        when (intent) {
            Intent.GetUsers -> getUsers2()
        }
    }

    private fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            emitState(State.Loading(true))
            val data = getGithubUsersUseCase(GetGithubUsersUseCase.Params(20, 0))
            emitState(State.UserList(data))
            emitState(State.Loading(false))
        }
    }

    private fun getUsers2() {
        viewModelScope.launch(Dispatchers.IO) {
            Pager(
                config = PagingConfig(
                    pageSize = USER_ITEMS_PER_PAGE,
                    enablePlaceholders = false,
                    initialLoadSize = USER_ITEMS_PER_PAGE
                ),
                pagingSourceFactory = { UserPagingSource(getGithubUsersUseCase) }
            ).flow.collectLatest {
                emitState(State.UserList2(it))
            }
        }
    }
}