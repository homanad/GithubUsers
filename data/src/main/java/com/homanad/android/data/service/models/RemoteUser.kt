package com.homanad.android.data.service.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class RemoteUser(
    val username: String,
    val avatarUrl: String,
    val htmlUrl: String,
    val location: String?,
    val followers: Int?,
    val following: Int?
)