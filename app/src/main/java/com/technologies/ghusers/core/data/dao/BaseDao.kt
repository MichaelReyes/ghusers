
package com.technologies.ghusers.core.data.dao

import androidx.room.*

/**
 * Base dao class for handling all common functions across DAO classes
 */
@Dao
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    suspend fun insert(vararg t: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    suspend fun insert(t: List<T>)

    @Update
    suspend fun update(vararg t: T)

    @Delete
    suspend fun delete(vararg t: T)
}