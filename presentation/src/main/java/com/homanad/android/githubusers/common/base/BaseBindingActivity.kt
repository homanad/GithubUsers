package com.homanad.android.githubusers.common.base

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * This class is the base class for Activity using Data Binding
 * @param B The DataBinding class that is automatically generated from the DataBinding function.
 */
abstract class BaseBindingActivity<B : ViewDataBinding> : AppCompatActivity() {

    private lateinit var _binding: B

    protected val binding: B
        get() = _binding

    protected open val isEdgeToEdge = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DataBindingUtil.setContentView(this, getContentRes())

        if (isEdgeToEdge) configEdgeToEdge()
        restoreInstanceState(savedInstanceState)
        setupUI()
        handleUIEvent()
        handleUIState()
    }

    private fun configEdgeToEdge() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    @LayoutRes
    protected abstract fun getContentRes(): Int

    protected open fun restoreInstanceState(instanceState: Bundle?) {}

    protected open fun setupUI() {}

    protected open fun handleUIEvent() {}

    protected open fun handleUIState() {}
}