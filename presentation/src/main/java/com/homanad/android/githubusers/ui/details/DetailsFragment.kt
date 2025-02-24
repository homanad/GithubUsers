package com.homanad.android.githubusers.ui.details

import androidx.navigation.fragment.navArgs
import com.homanad.android.githubusers.R
import com.homanad.android.githubusers.common.BaseBindingFragment
import com.homanad.android.githubusers.databinding.FragmentDetailsBinding

class DetailsFragment : BaseBindingFragment<FragmentDetailsBinding>() {

    override fun getContentRes() = R.layout.fragment_details

    private val args by navArgs<DetailsFragmentArgs>()

    override fun setupUI() {
        println("------------${args.username}")
    }
}