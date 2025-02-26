package com.homanad.android.githubusers.mappers

import com.homanad.android.domain.models.GithubUser
import com.homanad.android.githubusers.common.base.BaseMapper
import com.homanad.android.githubusers.models.UserItem

/**
 * This class is used to map data from GithubUser model (Domain layer) to UserItem model (UI layer).
 */
class UserItemMapper : BaseMapper<GithubUser, UserItem>() {

    override fun invoke(from: GithubUser): UserItem {
        return UserItem(from.username, from.avatarUrl, from.htmlUrl)
    }
}