package com.homanad.android.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class UserEntity(
    @PrimaryKey
    val username: String,
    val avatarUrl: String,
    val htmlUrl: String,
    val location: String?,
    val followers: Int?,
    val following: Int?,
    val blogUrl: String?,
    val updatedMillis: Long
)