package com.homanad.android.githubusers.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * This class is the base class for Fragment using Data Binding
 * @param B The DataBinding class that is automatically generated from the DataBinding function.
 */
abstract class BaseBindingFragment<B : ViewDataBinding> : Fragment() {

    private lateinit var _binding: B

    protected val binding: B
        get() = _binding

    open val loadingLayout: View? = null

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

    /**
     * Provide the layout resource id corresponding to the provided DataBinding class
     */
    @LayoutRes
    protected abstract fun getContentRes(): Int

    protected open fun bindData() {}

    protected open fun restoreInstanceState(instanceState: Bundle?) {}

    protected open fun setupUI() {}

    protected open fun handleUIEvent() {}

    protected open fun handleUIState() {}

    protected fun showLoading(isLoading: Boolean) {
        loadingLayout?.isVisible = isLoading
    }
}