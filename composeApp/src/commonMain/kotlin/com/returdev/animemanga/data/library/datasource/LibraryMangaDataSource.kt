package com.returdev.animemanga.data.library.datasource

import com.returdev.animemanga.data.library.dao.LibraryMangaDAO
import com.returdev.animemanga.data.library.model.LibraryOrderBy
import com.returdev.animemanga.data.library.model.entity.LibraryMangaEntity
import com.returdev.animemanga.domain.model.core.search.common.SortDirection
import com.returdev.animemanga.domain.model.library.UserLibraryStatusModel
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

/**
 * Data source responsible for managing manga entries in the user's library.
 *
 * @property mangaDAO The Data Access Object (DAO) used to perform database operations on manga entities.
 * @property transitionRunner A suspend lambda used to execute database operations within a transactional context.
 */
class LibraryMangaDataSource(
    private val mangaDAO : LibraryMangaDAO,
    private val transitionRunner : suspend (suspend () -> Unit) -> Unit
) {

    /**
     * Retrieves a list of manga filtered by their library status (e.g., Reading, Completed)
     * and sorted according to the provided parameters.
     *
     * @param status The library status to filter by (e.g., Reading, Completed, On Hold).
     * @param orderBy The field used for sorting (e.g., by title or date added).
     * @param sortDirection The direction of sorting (ascending or descending).
     * @param limit The maximum number of results to fetch per query (pagination limit).
     * @param offset The offset used for pagination to skip a number of results.
     *
     * @return A [Flow] emitting a list of [LibraryMangaEntity] matching the criteria.
     */
    fun getMangasByStatus(
        status : UserLibraryStatusModel,
        orderBy : LibraryOrderBy,
        sortDirection : SortDirection,
        limit : Int,
        offset : Int
    ) : Flow<List<LibraryMangaEntity>> {
        return when (sortDirection) {
            SortDirection.ASCENDANT -> mangaDAO.getMangaByStatus(
                status.name,
                orderBy.id,
                limit,
                offset
            )

            SortDirection.DESCENDANT -> mangaDAO.getMangaByStatusDesc(
                status.name,
                orderBy.id,
                limit,
                offset
            )
        }
    }

    /**
     * Retrieves the current library status of a specific manga.
     *
     * @param id The unique identifier of the manga.
     * @return The [UserLibraryStatusModel] representing the manga's current library status.
     */
    suspend fun getMangaStatusById(id : Int) : UserLibraryStatusModel {
        return UserLibraryStatusModel.valueOf(mangaDAO.getMangaStatusById(id))
    }

    /**
     * Inserts a new manga entry into the user's library.
     *
     * If the manga already exists (based on its ID), it will be ignored
     * due to the DAO's conflict resolution strategy.
     *
     * @param manga The [LibraryMangaEntity] to insert into the database.
     */
    suspend fun insertManga(manga : LibraryMangaEntity) {
        return mangaDAO.insertManga(manga)
    }

    /**
     * Updates the status of a manga in the user's library within a transactional context.
     *
     * The method compares the current stored status with the new one.
     * If they differ, it updates the status and sets the `added_date` field
     * to the current UTC date.
     *
     * @param id The unique identifier of the manga to update.
     * @param status The new [UserLibraryStatusModel] to assign.
     */
    @OptIn(ExperimentalTime::class)
    suspend fun updateMangaStatus(id : Int, status : UserLibraryStatusModel) {
        transitionRunner {
            val currentDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
            val currentStatus = getMangaStatusById(id)

            if (currentStatus != status) {
                mangaDAO.updateMangaStatus(status = status.name, date = currentDate, id = id)
            }
        }
    }

    /**
     * Deletes a manga entry from the user's library by its ID.
     *
     * @param id The unique identifier of the manga to delete.
     */
    suspend fun deleteManga(id : Int) {
        mangaDAO.deleteManga(id)
    }
}
