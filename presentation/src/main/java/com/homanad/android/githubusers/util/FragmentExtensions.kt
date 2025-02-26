package com.homanad.android.githubusers.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

val Fragment.viewLifecycleScope: LifecycleCoroutineScope
    get() = viewLifecycleOwner.lifecycleScope

fun Fragment.repeatOnLifecycleState(
    state: Lifecycle.State,
    block: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleScope.launch {
        repeatOnLifecycle(state) {
            block()
        }
    }
}