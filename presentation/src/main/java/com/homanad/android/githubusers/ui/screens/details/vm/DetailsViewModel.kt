package com.homanad.android.githubusers.ui.screens.details.vm

import androidx.lifecycle.viewModelScope
import com.homanad.android.domain.usecases.github.GetGithubUserUseCase
import com.homanad.android.githubusers.common.base.BaseViewModel
import com.homanad.android.githubusers.mappers.UserDataMapper
import com.homanad.android.githubusers.models.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getGithubUserUseCase: GetGithubUserUseCase,
    private val userDataMapper: UserDataMapper
) : BaseViewModel<DetailsViewModel.Intent, DetailsViewModel.State>() {

    sealed interface Intent {
        data class GetUser(val username: String) : Intent
    }

    sealed interface State {
        data class User(val user: UserData) : State
        data class Loading(val isLoading: Boolean) : State
    }

    override suspend fun processIntent(intent: Intent) {
        when (intent) {
            is Intent.GetUser -> getUser(intent.username)
        }
    }

    private fun getUser(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            emitState(State.Loading(true))
            getGithubUserUseCase(username).collectLatest {
                println("-----------data: $it")
                emitState(State.User(userDataMapper(it)))
                emitState(State.Loading(false))
            }
        }
    }
}