package com.homanad.android.githubusers.di

import android.content.Context
import com.homanad.android.githubusers.mappers.UserDetailsMapper
import com.homanad.android.githubusers.mappers.UserItemMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

/**
 * This is the Hilt DI module class, provide instances for mappers
 */
@Module
@InstallIn(ViewModelComponent::class)
object MapperModule {

    @Provides
    fun provideUserItemMapper() = UserItemMapper()

    @Provides
    fun provideUserDetailsMapper(
        @ApplicationContext context: Context,
        userItemMapper: UserItemMapper
    ) = UserDetailsMapper(context, userItemMapper)
}