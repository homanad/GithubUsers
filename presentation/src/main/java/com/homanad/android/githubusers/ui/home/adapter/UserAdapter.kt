package com.homanad.android.githubusers.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import coil3.load
import com.homanad.android.domain.models.GithubUser
import com.homanad.android.githubusers.common.BaseItemHolder
import com.homanad.android.githubusers.common.BaseRecyclerViewAdapter
import com.homanad.android.githubusers.databinding.ItemUserBinding

class UserAdapter : BaseRecyclerViewAdapter<GithubUser>() {

    inner class ItemHolder(
        private val binding: ItemUserBinding
    ) : BaseItemHolder<GithubUser>(binding) {

        override fun bind(item: GithubUser) {
            with(binding) {
                imgAvatar.load(item.avatarUrl)
                txtUsername.text = item.username
                txtHtmlUrl.text = item.htmlUrl
            }
        }
    }

    override fun getViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): BaseItemHolder<GithubUser> {
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return ItemHolder(binding)
    }
}