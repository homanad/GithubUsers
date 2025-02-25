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
            followerCount = convertToDisplay(from.followers),
            followingCount = convertToDisplay(from.following)
        )
    }

    private fun convertToDisplay(from: Int?): String {
        val count = from ?: return "0"

        val dividedValue = count.toFloat() / 10

        return if (isInt(dividedValue)) {
            "$count"
        } else {
            "${(dividedValue.toInt() * 10)}+"
        }
    }

    private fun isInt(n: Float): Boolean {
        return n % 1f == 0f
    }

}