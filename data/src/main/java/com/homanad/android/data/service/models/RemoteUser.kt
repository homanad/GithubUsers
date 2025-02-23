package com.homanad.android.data.service.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class RemoteUser(
    val login: String,
    val avatar_url: String,
    val html_url: String,
    val location: String?,
    val followers: Int?,
    val following: Int?
)