package com.returdev.animemanga.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.returdev.animemanga.data.cache.model.entity.YearCacheEntity

/**
 * Data Access Object (DAO) for managing season year entities in the local cache database.
 */
@Dao
interface YearCacheDAO {

    /**
     * Inserts a season year entity into the database.
     * If a conflict occurs (e.g., duplicate year), the insertion is ignored.
     *
     * @param year The [YearCacheEntity] object to be saved.
     */
    @Insert(onConflict = OnConflictStrategy.NONE)
    fun saveYear(year : YearCacheEntity)

    /**
     * Retrieves all season year entities from the database.
     *
     * @return List of [YearCacheEntity] objects.
     */
    @Query("SELECT * FROM season_year_table")
    fun getAllYears() : List<YearCacheEntity>

}