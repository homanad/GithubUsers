package com.homanad.android.githubusers.ui.screens.details

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.homanad.android.githubusers.R
import com.homanad.android.githubusers.common.base.BaseBindingFragment
import com.homanad.android.githubusers.databinding.FragmentDetailsBinding
import com.homanad.android.githubusers.models.UserDetails
import com.homanad.android.githubusers.ui.screens.details.vm.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : BaseBindingFragment<FragmentDetailsBinding>() {

    override fun getContentRes() = R.layout.fragment_details

    private val viewModel by viewModels<DetailsViewModel>()

    private val args by navArgs<DetailsFragmentArgs>()

    override val loadingLayout: View
        get() = binding.viewLoading.root

    override fun setupUI() {
        viewModel.sendIntent(DetailsViewModel.Intent.GetUser(args.username))
    }

    override fun handleUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.stateFlow.collectLatest {
                    handleState(it)
                }
            }
        }
    }

    private fun handleState(state: DetailsViewModel.State) {
        when (state) {
            is DetailsViewModel.State.User -> {
                showUserData(state.user)
            }

            is DetailsViewModel.State.Error -> {
                val errMessage = state.error.localizedMessage ?: getString(R.string.unknown_error)
                showError(errMessage)
            }

            is DetailsViewModel.State.Loading -> showLoading(state.isLoading)
        }
    }

    private fun showUserData(user: UserDetails) {
        with(binding) {
            userData = user
            isDetails = true
        }
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}