package com.homanad.android.githubusers.ui.details

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import coil3.load
import com.homanad.android.domain.models.GithubUser
import com.homanad.android.githubusers.R
import com.homanad.android.githubusers.common.BaseBindingFragment
import com.homanad.android.githubusers.databinding.FragmentDetailsBinding
import com.homanad.android.githubusers.ui.details.vm.DetailsViewModel
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

            is DetailsViewModel.State.Loading -> showLoading(state.isLoading)
        }
    }

    private fun showUserData(user: GithubUser) {
        println("-----------user: $user")

        with(binding) {
            cardUser.imgAvatar.load(user.avatarUrl)
            cardUser.txtUsername.text = user.username
            cardUser.txtHtmlUrl.text = user.htmlUrl
        }
    }
}