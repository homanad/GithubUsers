package com.homanad.android.githubusers.ui.screens.home.vm

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.homanad.android.githubusers.common.Constants.USER_ITEMS_PER_PAGE
import com.homanad.android.githubusers.common.base.BaseViewModel
import com.homanad.android.githubusers.models.UserItem
import com.homanad.android.githubusers.ui.screens.home.paging.UserPagingSource
import com.homanad.android.githubusers.util.launchIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userPagingSource: UserPagingSource
) : BaseViewModel<HomeViewModel.Intent, HomeViewModel.State>() {

    sealed interface Intent {
        data object GetUsers : Intent
    }

    sealed interface State {
        data class UserList(val users: PagingData<UserItem>) : State
        data class Loading(val isLoading: Boolean) : State
    }

    override suspend fun processIntent(intent: Intent) {
        when (intent) {
            Intent.GetUsers -> getUsers()
        }
    }

    private val userPagingFlow = Pager(
        config = PagingConfig(
            pageSize = USER_ITEMS_PER_PAGE,
            enablePlaceholders = false,
            initialLoadSize = USER_ITEMS_PER_PAGE
        ),
        pagingSourceFactory = { userPagingSource }
    ).flow.cachedIn(viewModelScope)

    private fun getUsers() {
        launchIO {
            userPagingFlow.collectLatest {
                emitState(State.UserList(it))
            }
        }
    }
}