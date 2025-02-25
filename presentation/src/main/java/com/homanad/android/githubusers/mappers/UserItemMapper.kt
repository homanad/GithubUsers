package com.homanad.android.githubusers.mappers

import com.homanad.android.domain.models.GithubUser
import com.homanad.android.githubusers.common.base.BaseMapper
import com.homanad.android.githubusers.models.UserItem

class UserItemMapper : BaseMapper<GithubUser, UserItem>() {

    override fun invoke(from: GithubUser): UserItem {
        return UserItem(from.username, from.avatarUrl, from.htmlUrl)
    }
}