package com.returdev.animemanga.data.cache.datasource.season

import androidx.room.Transaction
import com.returdev.animemanga.data.cache.dao.GenreCacheDAO
import com.returdev.animemanga.data.cache.model.entity.GenreCacheEntity

/**
 * Data source for managing genre entities in the local cache.
 *
 * @property genreCacheDAO The DAO used for genre cache operations.
 */
class GenreCacheDataSource(
    private val genreCacheDAO: GenreCacheDAO
) {

    /**
     * Synchronizes the local genres with a new list.
     * If the local genres differ from the new genres, clears and saves the new genres.
     *
     * @param newGenres The list of [GenreCacheEntity] to sync with the local cache.
     */
    fun syncGenres(newGenres: List<GenreCacheEntity>) : Boolean {
        val localGenres = genreCacheDAO.getGenres()

        if (!hasSameGenres(localGenres, newGenres)) {
            genreCacheDAO.replaceGenres(newGenres)
            return true
        }

        return false
    }

    /**
     * Retrieves a genre entity by its ID.
     *
     * @param id The unique identifier of the genre.
     * @return The [GenreCacheEntity] with the given ID, or null if not found.
     */
    fun getGenreById(id: Int): GenreCacheEntity? =
        genreCacheDAO.getGenreById(id)

    /**
     * Retrieves all genre entities from the local cache.
     *
     * @return A list of [GenreCacheEntity] objects.
     */
    fun getGenres(): List<GenreCacheEntity> =
        genreCacheDAO.getGenres()

    /**
     * Checks if two lists of genres are the same by comparing their sizes and sorted IDs.
     *
     * @param localGenres The current list of genres in the cache.
     * @param newGenres The new list of genres to compare.
     * @return True if both lists contain the same genres, false otherwise.
     */
    private fun hasSameGenres(
        localGenres: List<GenreCacheEntity>,
        newGenres: List<GenreCacheEntity>
    ): Boolean {
        if (localGenres.size != newGenres.size) return false
        return localGenres.sortedBy { it.id } == newGenres.sortedBy { it.id }
    }
}