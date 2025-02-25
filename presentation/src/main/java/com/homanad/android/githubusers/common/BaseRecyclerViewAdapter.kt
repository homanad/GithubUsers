package com.homanad.android.githubusers.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * This class is the base class for Recycler View Adapter
 * Provides some common functions for displaying/changing lists
 */
abstract class BaseRecyclerViewAdapter<T> : RecyclerView.Adapter<BaseItemHolder<T>>() {

    protected var items = mutableListOf<T>()
        private set

    /**
     * Set items for the list
     * @param items List of items to be displayed
     */
    fun setItems(items: List<T>) {
        val diffCallback = DiffCallback(this.items, items)
        this.items = items.toMutableList()
        DiffUtil.calculateDiff(diffCallback).dispatchUpdatesTo(this)
    }

    /**
     * Add an item to the end of the list
     * @param item Item to be added
     */
    fun addItem(item: T) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    /**
     * Add an item to the specified position
     * @param position Specified position
     * @param item Item to be added
     */
    fun addItem(position: Int, item: T) {
        items.add(position, item)
        notifyItemInserted(position)
    }

    /**
     * Add a list of items to the list
     * @param items List of items to be added
     */
    fun addItems(items: List<T>) {
        val lastPosition = itemCount
        this.items.addAll(items)
        notifyItemRangeInserted(lastPosition, items.size)
    }

    /**
     * Remove an item at the specified position
     * @param position Specified position
     */
    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    /**
     * Remove an item
     * @param item Item to be removed
     */
    fun removeItem(item: T) {
        val position = items.indexOf(item)
        removeItem(position)
    }

    /**
     * Get an item at the specified location
     * @param position Specified position
     */
    fun getItem(position: Int): T = items[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItemHolder<T> {
        val inflater = LayoutInflater.from(parent.context)
        return getViewHolder(inflater, parent, viewType)
    }

    abstract fun getViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): BaseItemHolder<T>

    override fun onBindViewHolder(holder: BaseItemHolder<T>, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class DiffCallback<T>(
    private val oldList: List<T>, private val newList: List<T>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}