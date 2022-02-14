package com.technologies.ghusers.core.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.technologies.ghusers.core.data.database.AppDatabase

@Entity(
    tableName = AppDatabase.NOTES
)
data class Note(
    @PrimaryKey
    val userId: Int,
    val notes: String
)
