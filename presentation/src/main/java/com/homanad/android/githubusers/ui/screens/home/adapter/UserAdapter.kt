package com.homanad.android.githubusers.ui.screens.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.homanad.android.githubusers.common.recycler.BaseItemHolder
import com.homanad.android.githubusers.common.recycler.BaseRecyclerViewAdapter
import com.homanad.android.githubusers.databinding.ItemUserBinding
import com.homanad.android.githubusers.models.UserItem

class UserAdapter(
    private val onClick: (String) -> Unit
) : BaseRecyclerViewAdapter<UserItem>() {

    inner class ItemHolder(
        private val binding: ItemUserBinding
    ) : BaseItemHolder<UserItem>(binding) {

        override fun bind(item: UserItem) {
            with(binding) {
                user = item
                isDetails = false
                root.setOnClickListener {
                    onClick(item.username)
                }
            }
        }
    }

    override fun getViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): BaseItemHolder<UserItem> {
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return ItemHolder(binding)
    }
}