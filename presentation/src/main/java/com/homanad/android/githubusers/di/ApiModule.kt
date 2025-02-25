package com.homanad.android.githubusers.di

import com.homanad.android.data.service.GithubService
import com.homanad.android.data.service.interceptors.CurlLoggerInterceptor
import com.homanad.android.githubusers.common.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * This is the Hilt DI module class, provide instances for classes involved in API calls
 */
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private fun getRetrofit() = Retrofit.Builder()
        .client(getOkHttpClient())
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            )
        )
        .build()

    private fun getOkHttpClient() = OkHttpClient.Builder()
        .callTimeout(Constants.API_TIMEOUT, TimeUnit.MILLISECONDS)
        .addInterceptor(CurlLoggerInterceptor())
        .build()

    @Provides
    fun provideGithubService(): GithubService = getRetrofit().create(GithubService::class.java)
}