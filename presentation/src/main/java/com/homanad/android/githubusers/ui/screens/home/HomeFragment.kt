package com.homanad.android.githubusers.ui.screens.home

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.homanad.android.githubusers.R
import com.homanad.android.githubusers.common.base.BaseBindingFragment
import com.homanad.android.githubusers.common.recycler.VerticalSpaceItemDecoration
import com.homanad.android.githubusers.databinding.FragmentHomeBinding
import com.homanad.android.githubusers.ui.screens.home.adapter.UserAdapter
import com.homanad.android.githubusers.ui.screens.home.vm.HomeViewModel
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

//    private val userAdapter2 by lazy {
//        UserPagingAdapter {
//            val direction = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it)
//            findNavController().navigate(direction)
//        }
//    }

    override val loadingLayout: View
        get() = binding.viewLoading.root

    override fun setupUI() {
        viewModel.sendIntent(HomeViewModel.Intent.GetUsers)
        with(binding) {
            rcvUsers.run {
                adapter = userAdapter
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

    private fun handleState(state: HomeViewModel.State) {
        when (state) {
            is HomeViewModel.State.UserList -> {
                userAdapter.setItems(state.users)
            }

//            is HomeViewModel.State.UserList2 -> {
//                userAdapter2.submitData(state.users)
//            }

            is HomeViewModel.State.Loading -> showLoading(state.isLoading)
        }
    }
}