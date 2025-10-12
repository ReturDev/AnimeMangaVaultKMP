package com.returdev.animemanga.data.library.dao

import androidx.annotation.VisibleForTesting
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.cash.paging.PagingSource
import com.returdev.animemanga.data.library.model.entity.LibraryAnimeEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

/**
 * Data Access Object (DAO) for managing the user's anime library.
 */
@Dao
interface LibraryAnimeDAO {

    /**
     * Retrieves a paginated list of anime by their library status (e.g., Watching, Completed).
     *
     * @param status The library status to filter by (e.g., "watching", "completed").
     * @param orderBy Determines the sort order:
     *   - `1` → Order by `added_date`.
     *   - `2` → Order by `title`.
     * @param limit The maximum number of items to return.
     * @param offset The offset to start retrieving items.
     *
     * @return A [PagingSource] providing paginated [LibraryAnimeEntity] items.
     */
    @Query(
        "SELECT * " +
                "FROM library_anime " +
                "WHERE status = :status " +
                "ORDER BY " +
                "CASE WHEN :orderBy = 1 THEN added_date END, " +
                "CASE WHEN :orderBy = 2 THEN title END " +
                "LIMIT :limit " +
                "OFFSET :offset "
    )
    fun getAnimesByStatus(
        status: String,
        orderBy: Int,
        limit: Int,
        offset: Int
    ): Flow<List<LibraryAnimeEntity>>

    /**
     * Same as [getAnimesByStatus], but sorts in **descending** order.
     *
     * @param status The library status to filter by.
     * @param orderBy Determines the descending sort order:
     *   - `1` → Order by `added_date` descending.
     *   - `2` → Order by `title` descending.
     * @param limit The maximum number of items to return.
     * @param offset The offset to start retrieving items.
     *
     * @return A [PagingSource] providing paginated [LibraryAnimeEntity] items.
     */
    @Query(
        "SELECT * " +
                "FROM library_anime " +
                "WHERE status = :status " +
                "ORDER BY " +
                "CASE WHEN :orderBy = 1 THEN added_date END DESC, " +
                "CASE WHEN :orderBy = 2 THEN title END DESC " +
                "LIMIT :limit " +
                "OFFSET :offset "
    )
    fun getAnimesByStatusDesc(
        status: String,
        orderBy: Int,
        limit: Int,
        offset: Int
    ): Flow<List<LibraryAnimeEntity>>

    /**
     * Retrieves the total number of anime entries in the library
     * that match a specific user-defined status (e.g., "watching", "completed").
     *
     * @param status The library status used to filter anime entries.
     * @return The total count of anime items with the given status.
     */
    @Query("SELECT COUNT(id) FROM library_anime WHERE status = :status")
    suspend fun getAnimesCountByStatus(status: String): Int


    /**
     * Retrieves the status of a specific anime by its ID.
     *
     * @param id The unique identifier of the anime.
     * @return The status string (e.g., "watching", "completed").
     */
    @Query("SELECT status FROM library_anime WHERE id = :id")
    suspend fun getAnimeStatusById(id: Int): String

    /**
     * Inserts a new anime into the library.
     *
     * If the anime already exists (based on its ID), the operation is ignored.
     *
     * @param anime The anime entity to insert.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAnime(anime: LibraryAnimeEntity)

    /**
     * Updates the status and added date of an anime in the library.
     *
     * @param status The new status to assign.
     * @param date The updated added date.
     * @param id The unique identifier of the anime to update.
     * @return The number of rows updated (0 if none).
     */
    @Query("UPDATE library_anime SET status = :status , added_date = :date WHERE id = :id")
    suspend fun updateAnimeStatus(status: String, date: LocalDate, id: Int): Int

    /**
     * Deletes a specific anime from the library by its ID.
     *
     * @param id The unique identifier of the anime.
     * @return The number of rows deleted (0 if none).
     */
    @Query("DELETE FROM library_anime WHERE id = :id")
    suspend fun deleteAnime(id: Int): Int

    /**
     * Deletes all anime entries from the library.
     *
     * ⚠️ Only exposed for testing purposes.
     */
    @VisibleForTesting
    @Query("DELETE FROM library_anime")
    suspend fun clearTable()
}
