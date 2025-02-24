package com.homanad.android.githubusers.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<T> : RecyclerView.Adapter<BaseItemHolder<T>>() {

    protected var items = mutableListOf<T>()
        private set

    fun setItems(items: List<T>) {
        val diffCallback = DiffCallback(this.items, items)
        this.items = items.toMutableList()
        DiffUtil.calculateDiff(diffCallback).dispatchUpdatesTo(this)
    }

    fun addItem(item: T) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun addItem(position: Int, item: T) {
        items.add(position, item)
        notifyItemInserted(position)
    }

    fun addItems(items: List<T>) {
        val lastPosition = itemCount
        this.items.addAll(items)
        notifyItemRangeInserted(lastPosition, items.size)
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun removeItem(item: T) {
        val position = items.indexOf(item)
        removeItem(position)
    }

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