package com.homanad.android.githubusers.ui.screens.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.homanad.android.githubusers.common.recycler.BaseItemHolder
import com.homanad.android.githubusers.common.recycler.BaseRecyclerViewAdapter
import com.homanad.android.githubusers.databinding.ItemUserBinding
import com.homanad.android.githubusers.models.UserData

class UserAdapter(
    private val onClick: (String) -> Unit
) : BaseRecyclerViewAdapter<UserData>() {

    inner class ItemHolder(
        private val binding: ItemUserBinding
    ) : BaseItemHolder<UserData>(binding) {

        override fun bind(item: UserData) {
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
    ): BaseItemHolder<UserData> {
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return ItemHolder(binding)
    }
}