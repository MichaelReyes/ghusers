package com.technologies.ghusers.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.technologies.ghusers.core.data.dao.NoteDao
import com.technologies.ghusers.core.data.dao.UserDao
import com.technologies.ghusers.core.data.entity.Note
import com.technologies.ghusers.core.data.entity.User

@Database(
    entities = [User::class, Note::class],
    version = AppDatabase.VERSION, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "ghusers.db"
        const val VERSION = 3
        const val USERS = "TBL_USERS"
        const val NOTES = "TBL_NOTES"
    }

    abstract fun userDao(): UserDao
    abstract fun noteDao(): NoteDao
}