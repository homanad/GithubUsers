package com.homanad.android.githubusers.common.recycler

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * This class is the base class for Recycler View Holder
 */
abstract class BaseItemHolder<T>(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    /**
     * An abstract function for subclasses to handle the display for each item
     */
    abstract fun bind(item: T)
}