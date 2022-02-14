package com.technologies.ghusers.core.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.technologies.ghusers.core.data.database.AppDatabase.Companion.NOTES
import com.technologies.ghusers.core.data.entity.Note
import io.reactivex.Single

@Dao
interface NoteDao: BaseDao<Note> {

    @Query("SELECT * FROM $NOTES WHERE userId = :userId")
    fun getUserNote(userId: Int): Note?

}