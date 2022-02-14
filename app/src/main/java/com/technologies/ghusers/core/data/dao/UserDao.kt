package com.technologies.ghusers.core.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.technologies.ghusers.core.data.database.AppDatabase.Companion.USERS
import com.technologies.ghusers.core.data.entity.SearchItem
import com.technologies.ghusers.core.data.entity.User

@Dao
interface UserDao : BaseDao<User> {

    @Query("SELECT * FROM $USERS")
    fun getUsers(): List<User>

    @Query("SELECT * FROM $USERS LIMIT :limit OFFSET :offset")
    fun getUsers(limit: Int, offset: Int): List<User>?

    @Query("SELECT * FROM $USERS WHERE login = :login")
    fun getUserByLogin(login: String): User?

    @Query("SELECT * FROM $USERS WHERE id IN (:id)")
    fun getUserById(id: List<Int>): List<User>?

    @Query("SELECT id FROM TBL_USERS WHERE login LIKE :search UNION ALL SELECT userId FROM TBL_NOTES WHERE notes LIKE :search")
    fun search(search: String): List<SearchItem>


}