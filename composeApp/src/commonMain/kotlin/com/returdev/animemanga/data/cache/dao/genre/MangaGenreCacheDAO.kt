package com.returdev.animemanga.data.cache.dao.genre

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.returdev.animemanga.data.cache.model.entity.genre.MangaGenreCacheEntity

@Dao
interface MangaGenreCacheDAO {

    /**
     * Inserts a list of manga genre entities into the database.
     * If a conflict occurs (e.g., duplicate id), the insertion is ignored.
     *
     * @param genres List of [MangaGenreCacheEntity] objects to be saved.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveGenres(genres: List<MangaGenreCacheEntity>)

    /**
     * Retrieves all manga genre entities from the database.
     *
     * @return List of [MangaGenreCacheEntity] objects.
     */
    @Query("SELECT * FROM manga_genres_table")
    suspend fun getGenres(): List<MangaGenreCacheEntity>

    /**
     * Retrieves a manga genre entity by its unique identifier.
     *
     * @param id The unique identifier of the genre.
     * @return [MangaGenreCacheEntity] corresponding to the given id.
     */
    @Query("SELECT * FROM manga_genres_table WHERE id = :id")
    suspend fun getGenreById(id: Int): MangaGenreCacheEntity

    /**
     * Deletes all manga genre entities from the database.
     */
    @Query("DELETE FROM manga_genres_table")
    suspend fun clearGenres()

    /**
     * Replaces all manga genre entities in the database with a new list.
     * First clears the table, then inserts the provided genres.
     *
     * @param genres List of [MangaGenreCacheEntity] objects to be saved.
     */
    @Transaction
    suspend fun replaceGenres(genres: List<MangaGenreCacheEntity>) {
        clearGenres()
        saveGenres(genres)
    }

}