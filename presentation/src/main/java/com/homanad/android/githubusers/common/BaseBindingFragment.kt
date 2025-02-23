package com.homanad.android.githubusers.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseBindingFragment<B : ViewDataBinding> : Fragment() {

    private lateinit var _binding: B

    protected val binding: B
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(inflater, getContentRes(), container, false)
        _binding.lifecycleOwner = viewLifecycleOwner
        bindData()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        restoreInstanceState(savedInstanceState)
        setupUI()
        handleUIEvent()
        handleUIState()
    }

    @LayoutRes
    protected abstract fun getContentRes(): Int

    protected open fun bindData() {}

    protected open fun restoreInstanceState(instanceState: Bundle?) {}

    protected open fun setupUI() {}

    protected open fun handleUIEvent() {}

    protected open fun handleUIState() {}
}