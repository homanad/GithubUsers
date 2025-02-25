package com.homanad.android.domain.models

data class GithubUser(
    val username: String,
    val avatarUrl: String,
    val htmlUrl: String,
    val location: String?,
    val followers: Int?,
    val following: Int?,
    val blogUrl: String?
)