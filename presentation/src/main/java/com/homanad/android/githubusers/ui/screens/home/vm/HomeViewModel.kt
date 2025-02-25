package com.homanad.android.githubusers.ui.screens.home.vm

import androidx.lifecycle.viewModelScope
import com.homanad.android.domain.usecases.github.GetGithubUsersUseCase
import com.homanad.android.githubusers.common.base.BaseViewModel
import com.homanad.android.githubusers.mappers.UserDataMapper
import com.homanad.android.githubusers.models.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGithubUsersUseCase: GetGithubUsersUseCase,
    private val userDataMapper: UserDataMapper
) : BaseViewModel<HomeViewModel.Intent, HomeViewModel.State>() {

    sealed interface Intent {
        data object GetUsers : Intent
    }

    sealed interface State {
        data class UserList(val users: List<UserData>) : State

        //        data class UserList2(val users: PagingData<GithubUser>) : State
        data class Loading(val isLoading: Boolean) : State
    }

    override suspend fun processIntent(intent: Intent) {
        when (intent) {
            Intent.GetUsers -> getUsers()
        }
    }

    private fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            emitState(State.Loading(true))
            val data = getGithubUsersUseCase(GetGithubUsersUseCase.Params(20, 0))
            emitState(State.UserList(data.map { userDataMapper(it) }))
            emitState(State.Loading(false))
        }
    }

//    private fun getUsers2() {
//        viewModelScope.launch(Dispatchers.IO) {
//            Pager(
//                config = PagingConfig(
//                    pageSize = USER_ITEMS_PER_PAGE,
//                    enablePlaceholders = false,
//                    initialLoadSize = USER_ITEMS_PER_PAGE
//                ),
//                pagingSourceFactory = { UserPagingSource(getGithubUsersUseCase) }
//            ).flow.collectLatest {
//                emitState(State.UserList2(it))
//            }
//        }
//    }
}