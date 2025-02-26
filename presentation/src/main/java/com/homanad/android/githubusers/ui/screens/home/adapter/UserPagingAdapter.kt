package com.homanad.android.githubusers.ui.screens.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.homanad.android.githubusers.common.recycler.BaseItemHolder
import com.homanad.android.githubusers.databinding.ItemUserBinding
import com.homanad.android.githubusers.models.UserItem

class UserPagingAdapter(
    private val onClick: (String) -> Unit
) : PagingDataAdapter<UserItem, UserPagingAdapter.ItemHolder>(UserItemCallback()) {

    inner class ItemHolder(
        private val binding: ItemUserBinding
    ) : BaseItemHolder<UserItem?>(binding) {

        override fun bind(item: UserItem?) {
            with(binding) {
                item?.let {
                    user = item
                    isDetails = false
                    root.setOnClickListener {
                        onClick(item.username)
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return ItemHolder(binding)
    }
}


class UserItemCallback : DiffUtil.ItemCallback<UserItem>() {

    override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
        return oldItem.username == newItem.username
    }

    override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
        return oldItem == newItem
    }
}