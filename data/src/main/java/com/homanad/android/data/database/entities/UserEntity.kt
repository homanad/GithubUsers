package com.homanad.android.data.database.entities

import androidx.room.Entity

@Entity(tableName = "users")
class UserEntity(
    val username: String,
    val avatarUrl: String,
    val htmlUrl: String,
    val location: String?,
    val followers: Int?,
    val following: Int?
)