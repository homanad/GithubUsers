package com.homanad.android.githubusers.mappers

import android.content.Context
import com.homanad.android.domain.models.GithubUser
import com.homanad.android.githubusers.R
import com.homanad.android.githubusers.common.base.BaseMapper
import com.homanad.android.githubusers.models.UserDetails

/**
 * This class is used to map data from GithubUser model (Domain layer) to UserDetails model (UI layer).
 */
class UserDetailsMapper(
    private val context: Context,
    private val userItemMapper: UserItemMapper
) : BaseMapper<GithubUser, UserDetails>() {

    override fun invoke(from: GithubUser): UserDetails {
        return UserDetails(
            userItem = userItemMapper(from),
            location = from.location ?: context.getString(R.string.unknown),
            followerCount = convertToDisplay(from.followers),
            followingCount = convertToDisplay(from.following),
            blogUrl = from.blogUrl
        )
    }

    /**
     * This function will convert the follower/following value into a string for display.
     */
    private fun convertToDisplay(value: Int?): String {
        return when (value) {
            0 -> "0"
            null -> context.getString(R.string.unknown)
            in 1..999 -> convertRoundDown(value, 10)
            else -> convertRoundDown(value, 100)
        }
    }

    /**
     * This function is used to round a number down to the nearest number,
     * Depending on its value, the nearest rounded value will change:
     * - Values from 0 to 999 will be rounded to the nearest ten.
     * - Values greater than 1000 will be rounded to the nearest hundred.
     * @see value Value to be rounded.
     * @see downTo The nearest value will be rounded.
     */
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