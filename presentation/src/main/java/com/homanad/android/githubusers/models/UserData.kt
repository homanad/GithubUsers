package com.homanad.android.githubusers.models

data class UserData(
    val username: String,
    val avatarUrl: String,
    val githubUrl: String,
    val location: String,
    val followerCount: Int,
    val followingCount: Int
)