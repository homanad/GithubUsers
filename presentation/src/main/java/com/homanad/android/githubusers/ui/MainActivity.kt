package com.homanad.android.githubusers.ui

import com.homanad.android.githubusers.R
import com.homanad.android.githubusers.common.BaseBindingActivity
import com.homanad.android.githubusers.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    override val isEdgeToEdge: Boolean
        get() = true

    override fun getContentRes() = R.layout.activity_main
}