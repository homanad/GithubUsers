package com.homanad.android.githubusers.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun ViewModel.launchDefault(block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch(Dispatchers.Default) { block() }
}