package com.returdev.animemanga.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.returdev.animemanga.data.cache.model.entity.GenreCacheEntity

/**
 * Data Access Object (DAO) for managing genre entities in the local cache database.
 */
@Dao
interface GenresCacheDAO {

    /**
     * Inserts a list of genre entities into the database.
     * If a conflict occurs (e.g., duplicate id), the insertion is ignored.
     *
     * @param genres List of [GenreCacheEntity] objects to be saved.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveGenres(genres: List<GenreCacheEntity>)

    /**
     * Retrieves all genre entities from the database.
     *
     * @return List of [GenreCacheEntity] objects.
     */
    @Query("SELECT * FROM genres_table")
    fun getGenres(): List<GenreCacheEntity>

    /**
     * Retrieves a genre entity by its unique identifier.
     *
     * @param id The unique identifier of the genre.
     * @return [GenreCacheEntity] corresponding to the given id.
     */
    @Query("SELECT * FROM genres_table WHERE id = :id")
    fun getGenreById(id: Int): GenreCacheEntity

    /**
     * Deletes all genre entities from the database.
     */
    @Query("DELETE FROM genres_table")
    fun clearGenres()

}