package com.homanad.android.githubusers.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

/** Base class for view model
 * @param Intent A sealed class/interface that defines the intents that should be handled for this ViewModel
 * @param State A sealed class/interface that defines the display states for the UI
 * @param intentChannelSize Specify the size of the Intent Channel
 * @param stateChannelSize Specify the size of the State Channel
 */
abstract class BaseViewModel<Intent, State>(
    intentChannelSize: Int = Channel.UNLIMITED,
    stateChannelSize: Int = Channel.BUFFERED
) : ViewModel() {

    private val initialised = AtomicBoolean(false)

    private val _intentChannel = Channel<Intent>(intentChannelSize)

    private val _stateChannel = Channel<State>(stateChannelSize)

    val stateFlow: Flow<State> = _stateChannel.receiveAsFlow()

    init {
        start()
    }

    /**
     * This function will be called as soon as the ViewModel is initialized.
     */
    private fun start() {
        if (initialised.compareAndSet(false, true)) {
            onFirstStart()
            viewModelScope.launch(Dispatchers.IO) {
                for (intent in _intentChannel) {
                    launch {
                        processIntent(intent)
                    }
                }
            }
        }
    }

    /**
     * An open function so that subclasses can define what to do on the first instantiation of the ViewModel
     */
    protected open fun onFirstStart() {}

    /**
     * An abstract function for subclasses to handle each respective Intent
     */
    protected abstract suspend fun processIntent(intent: Intent)

    /**
     * Send an Intent to the viewmodel to handle
     * @param intent the intent needs to be handled
     */
    fun sendIntent(intent: Intent) {
        viewModelScope.launch {
            _intentChannel.send(intent)
        }
    }

    /**
     * Emit a State to the UI
     * @param state The state to be emitted
     */
    protected fun emitState(state: State) {
        viewModelScope.launch {
            _stateChannel.send(state)
        }
    }
}