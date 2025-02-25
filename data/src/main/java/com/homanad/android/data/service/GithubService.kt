package com.homanad.android.data.service

import com.homanad.android.data.service.models.RemoteUser
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("/users")
    suspend fun getUsers(
        @Query("per_page") perPage: Int,
        @Query("since") since: Int

    ): List<RemoteUser>

    @GET("/users/{login_username}")
    suspend fun getUser(
        @Path("login_username") username: String
    ): RemoteUser
}