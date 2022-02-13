package com.technologies.ghusers.core.network

import com.technologies.ghusers.core.data.entity.User
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersService @Inject constructor(retrofit: Retrofit) : UsersApi {
    private val api by lazy { retrofit.create(UsersApi::class.java) }

    override suspend fun getUsers(start: Int, perPage: Int): List<User> = api.getUsers(start, perPage)

    override suspend fun getUserDetails(login: String): User = api.getUserDetails(login)
}