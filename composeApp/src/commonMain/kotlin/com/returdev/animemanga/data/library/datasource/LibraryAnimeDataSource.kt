package com.returdev.animemanga.data.library.datasource

import com.returdev.animemanga.data.library.dao.LibraryAnimeDAO
import com.returdev.animemanga.data.library.model.LibraryOrderBy
import com.returdev.animemanga.data.library.model.entity.LibraryAnimeEntity
import com.returdev.animemanga.domain.model.core.search.common.SortDirection
import com.returdev.animemanga.domain.model.library.UserLibraryStatusModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

/**
 * Data source responsible for managing anime entries stored in the user's library.
 *
 *
 * @property animeDAO The Data Access Object (DAO) used to interact with the anime library table.
 * @property transactionRunner A suspend lambda that executes database operations in a transaction-safe manner.
 */
class LibraryAnimeDataSource(
    private val animeDAO : LibraryAnimeDAO,
    private val transactionRunner : suspend (suspend () -> Unit) -> Unit
) {

    /**
     * Retrieves a list of anime from the user's library filtered by status and ordered according to the provided parameters.
     *
     * @param status The library status to filter by (e.g., Watching, Completed, Dropped).
     * @param orderBy The field used for sorting (e.g., by date added or title).
     * @param sortDirection The direction of sorting (ascending or descending).
     * @param limit The maximum number of anime to retrieve.
     * @param offset The starting offset for pagination.
     * @return A [Flow] emitting a list of [LibraryAnimeEntity] matching the provided criteria.
     */
    fun getAnimesByStatus(
        status : UserLibraryStatusModel,
        orderBy : LibraryOrderBy,
        sortDirection : SortDirection,
        limit : Int,
        offset : Int
    ) : Flow<List<LibraryAnimeEntity>> {
        return when (sortDirection) {
            SortDirection.ASCENDANT -> animeDAO.getAnimesByStatus(
                status.name,
                orderBy.id,
                limit,
                offset
            )

            SortDirection.DESCENDANT -> animeDAO.getAnimesByStatusDesc(
                status.name,
                orderBy.id,
                limit,
                offset
            )
        }
    }

    /**
     * Returns the total number of anime entries in the library for a specific status.
     *
     * @param status The library status (e.g., Watching, Completed).
     * @return The total count of anime with the given status.
     */
    suspend fun getAnimesCountByStatus(status : UserLibraryStatusModel) : Int {
        return animeDAO.getAnimesCountByStatus(status.name)
    }

    /**
     * Returns a stream of the library status for the anime with the given ID.
     *
     * @param id The unique identifier of the anime whose status should be retrieved.
     * @return A [Flow] emitting the status as a nullable [UserLibraryStatusModel].
     */
    fun getAnimeStatusById(id : Int) : Flow<UserLibraryStatusModel?> {
        return animeDAO.getAnimeStatusById(id).map{value -> value?.let { UserLibraryStatusModel.valueOf(it) }}
    }

    /**
     * Inserts a new anime entry into the library.
     *
     * If the anime already exists, it will be ignored due to the DAO's insert conflict strategy.
     *
     * @param anime The [LibraryAnimeEntity] to insert into the database.
     */
    suspend fun insertAnime(anime : LibraryAnimeEntity) {
        return animeDAO.insertAnime(anime)
    }

    /**
     * Updates the status of an anime in the library, ensuring transactional consistency.
     *
     * If the status has changed, the method updates both the status and the added date to the current UTC date.
     *
     * @param id The unique identifier of the anime to update.
     * @param status The new [UserLibraryStatusModel] to assign.
     */
    @OptIn(ExperimentalTime::class)
    suspend fun updateAnimeStatus(id : Int, status : UserLibraryStatusModel) {
        transactionRunner {
            val currentDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
            val currentStatus = getAnimeStatusById(id)

            if (currentStatus.firstOrNull() != status) {
                animeDAO.updateAnimeStatus(status = status.name, date = currentDate, id = id)
            }
        }
    }

    /**
     * Deletes an anime from the library by its ID.
     *
     * @param id The unique identifier of the anime to delete.
     */
    suspend fun deleteAnime(id : Int) {
        animeDAO.deleteAnime(id)
    }
}
