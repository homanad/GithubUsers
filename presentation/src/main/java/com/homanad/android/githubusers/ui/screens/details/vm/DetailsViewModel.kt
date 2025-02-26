package com.homanad.android.githubusers.ui.screens.details.vm

import androidx.lifecycle.viewModelScope
import com.homanad.android.domain.common.RequestState
import com.homanad.android.domain.usecases.github.GetGithubUserUseCase
import com.homanad.android.githubusers.common.base.BaseViewModel
import com.homanad.android.githubusers.mappers.UserDetailsMapper
import com.homanad.android.githubusers.models.UserDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getGithubUserUseCase: GetGithubUserUseCase,
    private val userDetailsMapper: UserDetailsMapper
) : BaseViewModel<DetailsViewModel.Intent, DetailsViewModel.State>() {

    sealed interface Intent {
        data class GetUser(val username: String) : Intent
    }

    sealed interface State {
        data class User(val user: UserDetails) : State
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
                when (it) {
                    is RequestState.Loading -> emitState(State.Loading(true))

                    is RequestState.Data -> {
                        emitState(State.Loading(false))
                        emitState(State.User(userDetailsMapper(it.data)))
                    }

                    is RequestState.Error -> emitState(State.Loading(false))
                }
            }
        }
    }
}