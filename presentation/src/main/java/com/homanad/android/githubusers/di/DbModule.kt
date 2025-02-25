package com.homanad.android.githubusers.di

import android.content.Context
import androidx.room.Room
import com.homanad.android.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * This is the Hilt DI module class, provide instances for classes related to local data (Room)
 */
@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        AppDatabase::class.java.simpleName
    ).build()

    @Provides
    fun provideUserDao(appDatabase: AppDatabase) = appDatabase.userDao()
}