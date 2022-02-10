package com.technologies.ghusers.core.network

import com.technologies.ghusers.core.data.entity.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UsersApi {

    @GET("users")
    suspend fun getUsers(@Query("since") start: Int, @Query("per_page") perPage: Int): List<User>

    @GET("users/{user_login}")
    suspend fun getUserDetails(@Path("user_login") login: String): User

}