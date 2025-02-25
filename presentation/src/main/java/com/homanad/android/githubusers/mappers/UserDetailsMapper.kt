package com.homanad.android.githubusers.mappers

import android.content.Context
import com.homanad.android.domain.models.GithubUser
import com.homanad.android.githubusers.R
import com.homanad.android.githubusers.common.base.BaseMapper
import com.homanad.android.githubusers.models.UserDetails

class UserDetailsMapper(
    private val context: Context,
    private val userItemMapper: UserItemMapper
) : BaseMapper<GithubUser, UserDetails>() {

    override fun invoke(from: GithubUser): UserDetails {
        return UserDetails(
            userItem = userItemMapper(from),
            location = from.location ?: context.getString(R.string.unknown),
            followerCount = convertToDisplay(from.followers),
            followingCount = convertToDisplay(from.following)
        )
    }

    private fun convertToDisplay(value: Int?): String {
        return when (value) {
            0, null -> "0"
            in 1..999 -> convertRoundDown(value, 10)
            else -> convertRoundDown(value, 100)
        }
    }

    private fun convertRoundDown(value: Int, downTo: Int): String {
        val dividedValue = value.toFloat() / downTo
        return if (isInt(dividedValue)) {
            "$value"
        } else {
            "${(dividedValue.toInt() * downTo)}+"
        }
    }

    private fun isInt(n: Float): Boolean {
        return n % 1f == 0f
    }

}