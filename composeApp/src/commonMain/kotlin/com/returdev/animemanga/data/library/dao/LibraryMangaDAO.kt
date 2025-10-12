package com.returdev.animemanga.data.library.dao

import androidx.annotation.VisibleForTesting
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.returdev.animemanga.data.library.model.entity.LibraryMangaEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

/**
 * Data Access Object (DAO) for managing the user's manga library.
 */
@Dao
interface LibraryMangaDAO {

    /**
     * Retrieves a paginated list of manga by their library status (e.g., Reading, Completed).
     *
     * @param status The library status to filter by (e.g., "reading", "completed").
     * @param orderBy Determines the sorting method:
     * - `1` → Order by `added_date`.
     * - `2` → Order by `title`.
     *
     * @return A [PagingSource] providing paginated [LibraryMangaEntity] items.
     */
    @Query(
        "SELECT * " +
                "FROM library_manga " +
                "WHERE status = :status " +
                "ORDER BY " +
                "CASE WHEN :orderBy = 1 THEN added_date END, " +
                "CASE WHEN :orderBy = 2 THEN title END " +
                "LIMIT :limit " +
                "OFFSET :offset "
    )
    fun getMangaByStatus(
        status: String,
        orderBy: Int,
        limit : Int,
        offset : Int
    ): Flow<List<LibraryMangaEntity>>

    /**
     * Retrieves a paginated list of manga by status, sorted in **descending** order.
     *
     * @param status The library status to filter by.
     * @param orderBy Determines the sorting method:
     * - `1` → Order by `added_date` descending (newest first).
     * - `2` → Order by `title` descending (reverse alphabetical).
     *
     * @return A [PagingSource] providing paginated [LibraryMangaEntity] items.
     */
    @Query(
        "SELECT * " +
                "FROM library_manga " +
                "WHERE status = :status " +
                "ORDER BY " +
                "CASE WHEN :orderBy = 1 THEN added_date END DESC, " +
                "CASE WHEN :orderBy = 2 THEN title END DESC " +
                "LIMIT :limit " +
                "OFFSET :offset "
    )
    fun getMangaByStatusDesc(
        status: String,
        orderBy: Int,
        limit : Int,
        offset : Int
    ): Flow<List<LibraryMangaEntity>>

    /**
     * Retrieves the total number of manga entries in the library
     * that match a specific user-defined status (e.g., "reading", "completed").
     *
     * @param status The library status used to filter manga entries.
     * @return The total count of manga items with the given status.
     */
    @Query("SELECT COUNT(id) FROM library_manga WHERE status = :status")
    suspend fun getMangasCountByStatus(status : String) : Int

    /**
     * Retrieves the library status of a specific manga by its ID.
     *
     * @param id The unique identifier of the manga.
     * @return The status string (e.g., "reading", "completed").
     */
    @Query("SELECT status FROM library_manga WHERE id = :id")
    suspend fun getMangaStatusById(id: Int): String

    /**
     * Inserts a new manga into the library.
     *
     * If the manga already exists (based on its ID), the operation will be ignored.
     *
     * @param manga The [LibraryMangaEntity] object to insert.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertManga(manga: LibraryMangaEntity)

    /**
     * Updates the status and the added date of a manga in the library.
     *
     * @param status The new status to assign.
     * @param date The updated added date.
     * @param id The unique identifier of the manga to update.
     * @return The number of rows affected (0 if no match was found).
     */
    @Query("UPDATE library_manga SET status = :status , added_date = :date WHERE id = :id")
    suspend fun updateMangaStatus(status: String, date: LocalDate, id: Int): Int

    /**
     * Deletes a specific manga from the library by its ID.
     *
     * @param id The unique identifier of the manga.
     * @return The number of rows deleted (0 if no match was found).
     */
    @Query("DELETE FROM library_manga WHERE id = :id")
    suspend fun deleteManga(id: Int): Int

    /**
     * Deletes all manga entries from the library.
     *
     * ⚠️ Only exposed for testing purposes.
     */
    @VisibleForTesting
    @Query("DELETE FROM library_manga")
    suspend fun clearTable()
}
