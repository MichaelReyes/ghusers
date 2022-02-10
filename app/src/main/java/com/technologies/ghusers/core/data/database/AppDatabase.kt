package com.technologies.ghusers.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [],
    version = AppDatabase.VERSION, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "ghusers.db"
        const val VERSION = 1
    }

}