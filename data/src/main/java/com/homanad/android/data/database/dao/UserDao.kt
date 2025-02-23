package com.homanad.android.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.homanad.android.data.database.entities.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getAll(): List<UserEntity>

    @Query("SELECT * FROM users WHERE username = :username")
    fun getUserByUsername(username: String): UserEntity?

    @Insert
    fun insertUsers(users: List<UserEntity>)

    @Insert
    fun insertUser(user: UserEntity)
}