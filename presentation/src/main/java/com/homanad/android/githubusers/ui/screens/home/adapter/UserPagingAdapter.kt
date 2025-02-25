package com.homanad.android.githubusers.ui.screens.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import coil3.load
import com.homanad.android.domain.models.GithubUser
import com.homanad.android.githubusers.common.recycler.BaseItemHolder
import com.homanad.android.githubusers.databinding.ItemUserBinding

class UserPagingAdapter(
    private val onClick: (String) -> Unit
) : PagingDataAdapter<GithubUser, UserPagingAdapter.ItemHolder>(UserItemCallback()) {

    inner class ItemHolder(
        private val binding: ItemUserBinding
    ) : BaseItemHolder<GithubUser?>(binding) {

        override fun bind(item: GithubUser?) {
            with(binding) {
                item?.let {
                    imgAvatar.load(item.avatarUrl)
                    txtUsername.text = item.username
                    txtHtmlUrl.text = item.htmlUrl
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


class UserItemCallback : DiffUtil.ItemCallback<GithubUser>() {

    override fun areItemsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
        return oldItem.username == newItem.username
    }

    override fun areContentsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
        return oldItem == newItem
    }
}