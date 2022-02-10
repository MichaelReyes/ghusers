package com.technologies.ghusers.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.technologies.ghusers.core.data.dao.UserDao
import com.technologies.ghusers.core.data.entity.User

@Database(
    entities = [User::class],
    version = AppDatabase.VERSION, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "ghusers.db"
        const val VERSION = 1
        const val USERS = "TBL_USERS"
    }

    abstract fun userDao(): UserDao
}