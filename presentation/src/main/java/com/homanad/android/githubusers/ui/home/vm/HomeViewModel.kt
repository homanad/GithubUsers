package com.homanad.android.githubusers.ui.home.vm

import androidx.lifecycle.viewModelScope
import com.homanad.android.domain.models.GithubUser
import com.homanad.android.domain.usecases.github.GetGithubUsersUseCase
import com.homanad.android.githubusers.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    }

    override suspend fun processIntent(intent: Intent) {
        when (intent) {
            Intent.GetUsers -> getUsers()
        }
    }

    private fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = getGithubUsersUseCase(GetGithubUsersUseCase.Params(20, 0))
            emitState(State.UserList(data))
        }
    }
}