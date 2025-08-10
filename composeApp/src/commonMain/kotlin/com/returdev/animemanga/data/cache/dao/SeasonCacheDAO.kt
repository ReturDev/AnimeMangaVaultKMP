package com.returdev.animemanga.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.returdev.animemanga.data.cache.model.entity.SeasonCacheEntity

/**
 * Data Access Object (DAO) for managing season entities in the local cache database.
 */
@Dao
interface SeasonCacheDAO {

    /**
     * Inserts a season entity into the database.
     * If a conflict occurs (e.g., duplicate entry), the insertion is ignored.
     *
     * @param season The [SeasonCacheEntity] object to be saved.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveSeason(season : SeasonCacheEntity)

    /**
     * Retrieves all season entities for a specific year from the database.
     *
     * @param year The year to filter seasons by.
     * @return List of [SeasonCacheEntity] objects for the given year.
     */
    @Query("SELECT * FROM seasons_table WHERE year = :year")
    suspend fun getSeasonsByYear(
        year : Int
    ) : List<SeasonCacheEntity>

    /**
     * Retrieves the most recent season entity from the database,
     * ordered by year in descending order.
     *
     * @return The latest [SeasonCacheEntity] or null if none exist.
     */
    @Query("SELECT * FROM seasons_table ORDER BY year DESC LIMIT 1")
    suspend fun getLastSeason() : SeasonCacheEntity?

}