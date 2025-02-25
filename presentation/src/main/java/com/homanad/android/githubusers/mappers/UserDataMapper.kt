package com.homanad.android.githubusers.mappers

import android.content.Context
import com.homanad.android.domain.models.GithubUser
import com.homanad.android.githubusers.R
import com.homanad.android.githubusers.common.base.BaseMapper
import com.homanad.android.githubusers.models.UserData

class UserDataMapper(
    private val context: Context
) : BaseMapper<GithubUser, UserData>() {

    override fun invoke(from: GithubUser): UserData {
        return UserData(
            username = from.username,
            avatarUrl = from.avatarUrl,
            githubUrl = from.htmlUrl,
            location = from.location ?: context.getString(R.string.unknown),
            followerCount = from.followers ?: 0,
            followingCount = from.following ?: 0
        )
    }
}