package com.returdev.animemanga.data.cache.dao.genre

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.returdev.animemanga.data.cache.model.entity.genre.AnimeGenreCacheEntity

@Dao
interface AnimeGenreCacheDAO {

    /**
     * Inserts a list of genre entities into the database.
     * If a conflict occurs (e.g., duplicate id), the insertion is ignored.
     *
     * @param genres List of [AnimeGenreCacheEntity] objects to be saved.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveGenres(genres: List<AnimeGenreCacheEntity>)

    /**
     * Retrieves all genre entities from the database.
     *
     * @return List of [AnimeGenreCacheEntity] objects.
     */
    @Query("SELECT * FROM anime_genres_table")
    suspend fun getGenres(): List<AnimeGenreCacheEntity>

    /**
     * Retrieves a genre entity by its unique identifier.
     *
     * @param id The unique identifier of the genre.
     * @return [AnimeGenreCacheEntity] corresponding to the given id.
     */
    @Query("SELECT * FROM anime_genres_table WHERE id = :id")
    suspend fun getGenreById(id: Int): AnimeGenreCacheEntity

    /**
     * Deletes all genre entities from the database.
     */
    @Query("DELETE FROM anime_genres_table")
    suspend fun clearGenres()

    /**
     * Replaces all genre entities in the database with a new list.
     * First clears the table, then inserts the provided genres.
     *
     * @param genres List of [AnimeGenreCacheEntity] objects to be saved.
     */
    @Transaction
    suspend fun replaceGenres(genres: List<AnimeGenreCacheEntity>) {
        clearGenres()
        saveGenres(genres)
    }

}