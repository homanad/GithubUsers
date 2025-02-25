package com.homanad.android.data.mappers

import com.homanad.android.data.database.entities.UserEntity
import com.homanad.android.domain.models.GithubUser

fun UserEntity.toGithubUser() =
    GithubUser(username, avatarUrl, htmlUrl, location, followers, following, blogUrl)