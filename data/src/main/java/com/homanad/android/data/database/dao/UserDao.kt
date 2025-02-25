package com.homanad.android.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.homanad.android.data.database.entities.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getAll(): List<UserEntity>

    @Query("SELECT * FROM users WHERE username = :username")
    fun getUserByUsername(username: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(users: List<UserEntity>)

    @Update
    fun updateUser(user: UserEntity)

    @Query("DELETE FROM users")
    fun deleteAll()

    @Transaction
    fun deleteAndInsert(users: List<UserEntity>) {
        deleteAll()
        insertUsers(users)
    }
}