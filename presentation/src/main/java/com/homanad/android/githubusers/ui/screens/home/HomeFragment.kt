package com.homanad.android.githubusers.ui.screens.home

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.homanad.android.githubusers.R
import com.homanad.android.githubusers.common.base.BaseBindingFragment
import com.homanad.android.githubusers.common.recycler.VerticalSpaceItemDecoration
import com.homanad.android.githubusers.databinding.FragmentHomeBinding
import com.homanad.android.githubusers.ui.screens.home.adapter.UserPagingAdapter
import com.homanad.android.githubusers.ui.screens.home.vm.HomeViewModel
import com.homanad.android.githubusers.util.repeatOnLifecycleState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseBindingFragment<FragmentHomeBinding>() {

    override fun getContentRes() = R.layout.fragment_home

    private val viewModel by viewModels<HomeViewModel>()

    private val userAdapter2 by lazy {
        UserPagingAdapter {
            val direction = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it)
            findNavController().navigate(direction)
        }
    }

    override val loadingLayout: View
        get() = binding.viewLoading.root

    override fun setupUI() {
        viewModel.sendIntent(HomeViewModel.Intent.GetUsers)
        with(binding) {
            rcvUsers.run {
                adapter = userAdapter2
                layoutManager = LinearLayoutManager(requireContext())

                val verticalSpace = resources.getDimensionPixelSize(R.dimen.recyclerVerticalSpacing)
                addItemDecoration(
                    VerticalSpaceItemDecoration(
                        verticalSpace,
                        resources.getDimensionPixelSize(R.dimen.recyclerItemSpacing),
                        verticalSpace
                    )
                )
            }
        }
    }

    override fun handleUIEvent() {
        binding.btnRetry.setOnClickListener {
            userAdapter2.retry()
        }
    }

    override fun handleUIState() {
        repeatOnLifecycleState(Lifecycle.State.RESUMED) {
            launch {
                viewModel.stateFlow.collect {
                    handleState(it)
                }
            }
            launch {
                userAdapter2.loadStateFlow.collectLatest { loadStates ->
                    showLoading(loadStates.refresh is LoadState.Loading)
                    binding.layoutRetry.isVisible = loadStates.refresh is LoadState.Error
                    if (loadStates.refresh is LoadState.Error) {
                        binding.txtError.text =
                            (loadStates.refresh as LoadState.Error).error.localizedMessage
                    }
                }
            }
        }
    }

    private suspend fun handleState(state: HomeViewModel.State) {
        when (state) {
            is HomeViewModel.State.UserList -> {
                userAdapter2.submitData(state.users)
            }

            is HomeViewModel.State.Loading -> showLoading(state.isLoading)
        }
    }
}