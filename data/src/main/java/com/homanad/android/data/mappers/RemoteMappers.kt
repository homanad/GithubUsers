package com.homanad.android.data.mappers

import com.homanad.android.data.database.entities.UserEntity
import com.homanad.android.data.service.models.RemoteUser
import com.homanad.android.domain.models.GithubUser

fun RemoteUser.toUserEntity() =
    UserEntity(id, login, avatar_url, html_url, location, followers, following, blog, System.currentTimeMillis())

fun RemoteUser.toGithubUser() =
    GithubUser(login, avatar_url, html_url, location, followers, following, blog)