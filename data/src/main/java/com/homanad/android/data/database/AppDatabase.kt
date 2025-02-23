package com.homanad.android.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.homanad.android.data.database.dao.UserDao
import com.homanad.android.data.database.entities.UserEntity

@Database(
    entities = [UserEntity::class],
    exportSchema = true,
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}