package com.returdev.animemanga.data.repository

import com.returdev.animemanga.core.roompaging.RoomPager
import com.returdev.animemanga.core.roompaging.RoomPagingConfig
import com.returdev.animemanga.core.roompaging.RoomPagingSource
import com.returdev.animemanga.data.library.datasource.LibraryAnimeDataSource
import com.returdev.animemanga.data.library.datasource.LibraryMangaDataSource
import com.returdev.animemanga.data.library.model.LibraryOrderBy
import com.returdev.animemanga.data.library.model.entity.LibraryAnimeEntity
import com.returdev.animemanga.data.library.model.entity.LibraryMangaEntity
import com.returdev.animemanga.data.library.model.extension.toDomain
import com.returdev.animemanga.domain.model.anime.AnimeBasicModel
import com.returdev.animemanga.domain.model.core.ImageType
import com.returdev.animemanga.domain.model.core.search.common.SortDirection
import com.returdev.animemanga.domain.model.library.UserLibraryStatusModel
import com.returdev.animemanga.domain.model.manga.MangaBasicModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
/**
 * Repository responsible for managing library-related operations for both Anime and Manga.
 *
 * @param dispatcher Coroutine dispatcher used for performing background operations.
 * @param animeDataSource Data source handling Anime library database interactions.
 * @param mangaDataSource Data source handling Manga library database interactions.
 */
class LibraryRepository(
    private val dispatcher : CoroutineDispatcher,
    private val animeDataSource : LibraryAnimeDataSource,
    private val mangaDataSource : LibraryMangaDataSource
) {

    /** Default configuration for paginated data loading from Room. */
    private val pagingConfig = RoomPagingConfig(
        pageSize = 20,
        maxPages = 3
    )

    /**
     * Retrieves the library status of an anime by its ID.
     *
     * @param id The anime's unique identifier.
     * @return The corresponding [UserLibraryStatusModel].
     */
    fun getAnimeStatusById(id : Int) : Flow<UserLibraryStatusModel?> {
        return animeDataSource.getAnimeStatusById(id)
    }

    /**
     * Retrieves the library status of a manga by its ID.
     *
     * @param id The manga's unique identifier.
     * @return The corresponding [UserLibraryStatusModel].
     */
    fun getMangaStatusById(id : Int) : Flow<UserLibraryStatusModel?> {
        return mangaDataSource.getMangaStatusById(id)
    }

    /**
     * Creates a pager for loading Anime from the local database by status with pagination.
     *
     * @param status The anime library status (e.g., Watching, Completed).
     * @param orderBy Sorting criteria ([LibraryOrderBy]).
     * @param sortDirection Sort direction (Ascending or Descending).
     * @return A configured [RoomPager] that outputs mapped [AnimeBasicModel] items.
     */
    fun getAnimesByStatusPager(
        status : UserLibraryStatusModel,
        orderBy : LibraryOrderBy,
        sortDirection : SortDirection
    ) : RoomPager<LibraryAnimeEntity, AnimeBasicModel> {

        val pagingSource = RoomPagingSource(
            config = pagingConfig,
            getTotalItemCount = { animeDataSource.getAnimesCountByStatus(status) },
            fetchData = { limit, offset ->
                animeDataSource.getAnimesByStatus(
                    status = status,
                    orderBy = orderBy,
                    sortDirection = sortDirection,
                    limit = limit,
                    offset = offset
                )
            }
        )

        return RoomPager(
            dispatcher = dispatcher,
            pagingSource = pagingSource,
            mapper = { it.toDomain() }
        )
    }

    /**
     * Creates a pager for loading Manga from the local database by status with pagination.
     *
     * @param status The manga library status.
     * @param orderBy Sorting criteria.
     * @param sortDirection Sort direction (ASC/DESC).
     * @return A configured [RoomPager] to load and map [MangaBasicModel].
     */
    fun getMangasByStatus(
        status : UserLibraryStatusModel,
        orderBy : LibraryOrderBy,
        sortDirection : SortDirection
    ) : RoomPager<LibraryMangaEntity, MangaBasicModel> {

        val pagingSource = RoomPagingSource(
            config = pagingConfig,
            getTotalItemCount = { mangaDataSource.getMangasCountByStatus(status) },
            fetchData = { limit, offset ->
                mangaDataSource.getMangasByStatus(
                    status = status,
                    orderBy = orderBy,
                    sortDirection = sortDirection,
                    limit = limit,
                    offset = offset
                )
            }
        )

        return RoomPager(
            dispatcher = dispatcher,
            pagingSource = pagingSource,
            mapper = { it.toDomain() }
        )
    }

    /**
     * Saves a new anime entry to the library with a specific status.
     *
     * @param anime The anime data to save.
     * @param status The library status to assign.
     */
    suspend fun saveAnimeWithStatus(anime : AnimeBasicModel, status : UserLibraryStatusModel) {
        val currentDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
        animeDataSource.insertAnime(
            LibraryAnimeEntity(
                id = anime.id,
                image = anime.images.firstOrNull { it is ImageType.NormalImage }?.url.orEmpty(),
                title = anime.title,
                type = anime.type.orEmpty(),
                score = anime.score,
                addedDate = currentDate,
                status = status.name
            )
        )
    }

    /**
     * Saves a manga entry to the library with a chosen status.
     *
     * @param manga The manga to insert into the library.
     * @param status Desired library status.
     */
    suspend fun saveMangaWithStatus(manga : MangaBasicModel, status : UserLibraryStatusModel) {
        val currentDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
        mangaDataSource.insertManga(
            LibraryMangaEntity(
                id = manga.id,
                image = manga.images.firstOrNull { it is ImageType.NormalImage }?.url.orEmpty(),
                title = manga.title,
                type = manga.type.orEmpty(),
                score = manga.score,
                addedDate = currentDate,
                status = status.name
            )
        )
    }

    /**
     * Changes the library status of an anime entry.
     *
     * If [newStatus] is non-null, the anime's status is updated.
     * If [newStatus] is null, the anime entry is removed from the library.
     *
     * @param animeId The ID of the anime whose status should be modified.
     * @param newStatus The new status to assign, or `null` to remove the anime from the library.
     */
    suspend fun changeAnimeStatus(animeId : Int, newStatus : UserLibraryStatusModel?) {
        newStatus
            ?.let { status -> animeDataSource.updateAnimeStatus(animeId, status) }
            ?: animeDataSource.deleteAnime(animeId)
    }

    /**
     * Changes the library status of a manga entry.
     *
     * If [newStatus] is non-null, the manga's status is updated.
     * If [newStatus] is null, the manga entry is removed from the library.
     *
     * @param mangaId The ID of the manga whose status should be modified.
     * @param newStatus The new status to assign, or `null` to remove the manga from the library.
     */
    suspend fun changeMangaStatus(mangaId : Int, newStatus : UserLibraryStatusModel?) {
        newStatus
            ?.let { status -> mangaDataSource.updateMangaStatus(mangaId, status) }
            ?: mangaDataSource.deleteManga(mangaId)
    }


}
