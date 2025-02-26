package com.homanad.android.githubusers.ui.screens.home.vm

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.homanad.android.domain.usecases.github.GetGithubUsersUseCase
import com.homanad.android.githubusers.common.Constants.USER_ITEMS_PER_PAGE
import com.homanad.android.githubusers.common.base.BaseViewModel
import com.homanad.android.githubusers.mappers.UserItemMapper
import com.homanad.android.githubusers.models.UserItem
import com.homanad.android.githubusers.ui.screens.home.paging.UserPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGithubUsersUseCase: GetGithubUsersUseCase,
    private val userItemMapper: UserItemMapper
) : BaseViewModel<HomeViewModel.Intent, HomeViewModel.State>() {

    sealed interface Intent {
        data object GetUsers : Intent
    }

    sealed interface State {
        data class UserList(val users: List<UserItem>) : State

        data class UserList2(val users: PagingData<UserItem>) : State
        data class Loading(val isLoading: Boolean) : State
    }

    override suspend fun processIntent(intent: Intent) {
        when (intent) {
            Intent.GetUsers -> getUsers2()
        }
    }

    private val userPagingFlow = Pager(
        config = PagingConfig(
            pageSize = USER_ITEMS_PER_PAGE,
            enablePlaceholders = false,
            initialLoadSize = USER_ITEMS_PER_PAGE
        ),
        pagingSourceFactory = { UserPagingSource(getGithubUsersUseCase, userItemMapper) }
    ).flow.cachedIn(viewModelScope)

    private fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            emitState(State.Loading(true))
            val data = getGithubUsersUseCase(GetGithubUsersUseCase.Params(20, 0))
            emitState(State.UserList(data.map { userItemMapper(it) }))
            emitState(State.Loading(false))
        }
    }

    private fun getUsers2() {
        viewModelScope.launch(Dispatchers.IO) {
            userPagingFlow.collectLatest {
                emitState(State.UserList2(it))
            }
        }
    }
}