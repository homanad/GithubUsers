package com.homanad.android.githubusers.di

import android.content.Context
import com.homanad.android.githubusers.mappers.UserDataMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object MapperModule {

    @Provides
    fun provideUserDetailsMapper(@ApplicationContext context: Context) = UserDataMapper(context)
}