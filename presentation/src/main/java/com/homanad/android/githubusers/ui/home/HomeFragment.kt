package com.homanad.android.githubusers.ui.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.homanad.android.githubusers.R
import com.homanad.android.githubusers.common.BaseBindingFragment
import com.homanad.android.githubusers.common.VerticalSpaceItemDecoration
import com.homanad.android.githubusers.databinding.FragmentHomeBinding
import com.homanad.android.githubusers.ui.home.adapter.UserAdapter
import com.homanad.android.githubusers.ui.home.adapter.UserPagingAdapter
import com.homanad.android.githubusers.ui.home.vm.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseBindingFragment<FragmentHomeBinding>() {

    override fun getContentRes() = R.layout.fragment_home

    private val viewModel by viewModels<HomeViewModel>()

    private val userAdapter by lazy {
        UserAdapter {
            val direction = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it)
            findNavController().navigate(direction)
        }
    }

    private val userAdapter2 by lazy {
        UserPagingAdapter {
            val direction = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it)
            findNavController().navigate(direction)
        }
    }

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

    override fun handleUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.stateFlow.collect {
                    handleState(it)
                }
            }
        }
    }

    private suspend fun handleState(state: HomeViewModel.State) {
        when (state) {
            is HomeViewModel.State.UserList -> {
                userAdapter.setItems(state.users)
            }

            is HomeViewModel.State.UserList2 -> {
                userAdapter2.submitData(state.users)
            }
        }
    }
}