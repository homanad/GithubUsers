package com.homanad.android.githubusers.ui.screens.details.vm

import com.homanad.android.domain.common.RequestState
import com.homanad.android.domain.usecases.github.GetGithubUserUseCase
import com.homanad.android.githubusers.common.base.BaseViewModel
import com.homanad.android.githubusers.mappers.UserDetailsMapper
import com.homanad.android.githubusers.models.UserDetails
import com.homanad.android.githubusers.util.launchIO
import com.homanad.android.githubusers.util.safeFlow
import dagger.hilt.android.lifecycle.HiltViewModel
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
        data class Error(val error: Throwable) : State
    }

    override suspend fun processIntent(intent: Intent) {
        when (intent) {
            is Intent.GetUser -> getUser(intent.username)
        }
    }

    private fun getUser(username: String) {
        launchIO {
            safeFlow(
                getGithubUserUseCase(username),
                onLoading = { emitState(State.Loading(true)) },
                onError = { emitState(State.Error(it)) },
                onCompletion = { emitState(State.Loading(false)) },
            ) {
                when (it) {
                    is RequestState.Loading -> emitState(State.Loading(true))

                    is RequestState.Data -> {
                        emitState(State.User(userDetailsMapper(it.data)))
                    }
                }
            }
        }
    }
}