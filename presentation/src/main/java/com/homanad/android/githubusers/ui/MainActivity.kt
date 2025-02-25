package com.homanad.android.githubusers.ui

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.homanad.android.githubusers.R
import com.homanad.android.githubusers.common.base.BaseBindingActivity
import com.homanad.android.githubusers.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseBindingActivity<ActivityMainBinding>(),
    NavController.OnDestinationChangedListener {

    override val isEdgeToEdge: Boolean
        get() = true

    override fun getContentRes() = R.layout.activity_main

    private val navHostFragment: NavHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
    }
    private val navController: NavController by lazy {
        navHostFragment.navController
    }

    override fun setupUI() {
        navController.addOnDestinationChangedListener(this)
        binding.btnBack.setOnClickListener {
            navController.navigateUp()
        }
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        with(binding) {
            txtLabel.text = destination.label
            btnBack.isVisible = destination.id != R.id.homeFragment
        }
    }
}