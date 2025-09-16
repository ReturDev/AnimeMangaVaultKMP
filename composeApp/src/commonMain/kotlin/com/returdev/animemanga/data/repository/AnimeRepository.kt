package com.returdev.animemanga.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.returdev.animemanga.core.Logger
import com.returdev.animemanga.data.cache.datasource.CacheMetadataDataSource
import com.returdev.animemanga.data.cache.datasource.SeasonCacheDataSource
import com.returdev.animemanga.data.cache.datasource.genre.AnimeGenreCacheDataSource
import com.returdev.animemanga.data.cache.model.extension.toAnimeGenreCacheList
import com.returdev.animemanga.data.cache.model.extension.toCacheEntityList
import com.returdev.animemanga.data.cache.model.extension.toDomainListAnime
import com.returdev.animemanga.data.model.extension.toLowerCase
import com.returdev.animemanga.data.paging.AnimePagingSource
import com.returdev.animemanga.data.remote.model.core.extension.toDomainModel
import com.returdev.animemanga.data.remote.model.core.extension.toPagedDomainModel
import com.returdev.animemanga.data.remote.repository.AnimeRemoteRepository
import com.returdev.animemanga.data.remote.service.ApiService
import com.returdev.animemanga.data.repository.AnimeRepository.Companion.MONTHS_FOR_GENRES_UPDATE
import com.returdev.animemanga.data.repository.AnimeRepository.Companion.MONTHS_FOR_SEASON_UPDATE
import com.returdev.animemanga.data.repository.core.RepositoryUtil
import com.returdev.animemanga.domain.model.anime.AnimeBasicModel
import com.returdev.animemanga.domain.model.anime.AnimeModel
import com.returdev.animemanga.domain.model.core.Season
import com.returdev.animemanga.domain.model.core.result.DomainResult
import com.returdev.animemanga.domain.model.core.search.SearchFilters
import com.returdev.animemanga.domain.model.core.search.anime.AnimeTypeFilters
import kotlinx.coroutines.flow.Flow

/**
 * Repository responsible for retrieving, caching, and updating anime-related data.
 *
 * Acts as the middle layer between remote data sources ([AnimeRemoteRepository]),
 * cached data sources ([SeasonCacheDataSource], [AnimeGenreCacheDataSource], [CacheMetadataDataSource]),
 * and the UI layer that consumes paging flows.
 *
 * @property animeRepository Remote repository for fetching anime data from the API.
 * @property animePagingSource Paging source factory for providing anime lists.
 * @property seasonDataSource Local cache for storing and retrieving season-related data.
 * @property genreDataSource Local cache for storing and syncing anime genres.
 * @property metadataDataSource Metadata cache for managing update timestamps and user info.
 */
class AnimeRepository(
    private val animeRepository : AnimeRemoteRepository,
    private val animePagingSource : AnimePagingSource,
    private val seasonDataSource : SeasonCacheDataSource,
    private val genreDataSource : AnimeGenreCacheDataSource,
    private val metadataDataSource : CacheMetadataDataSource
) {

    companion object {
        /** Number of items per page when loading anime. */
        const val PAGE_SIZE = ApiService.MAX_REQUEST_LIMIT

        /** Number of items to prefetch ahead of the current page. */
        const val PREFETCH_ITEMS = 5

        /** Time interval (in months) before cached season data should be refreshed. */
        private const val MONTHS_FOR_SEASON_UPDATE = 1

        /** Time interval (in months) before cached genre data should be refreshed. */
        private const val MONTHS_FOR_GENRES_UPDATE = 2
    }

    /**
     * Retrieves detailed anime information by its [id].
     *
     * @param id Anime ID.
     * @return A [DomainResult] containing an [AnimeModel].
     */
    suspend fun getAnimeById(id : Int) : DomainResult<AnimeModel> {
        return animeRepository.getAnimeById(id).toDomainModel { it.toPagedDomainModel() }
    }

    /**
     * Searches for anime based on a query and filter options.
     *
     * @param query Search query string.
     * @param filters Filtering options for the search.
     * @return A [Flow] of [PagingData] containing [AnimeBasicModel] results.
     */
    suspend fun getAnimeSearch(
        query : String,
        filters : SearchFilters.AnimeFilters,
    ) : Flow<PagingData<AnimeBasicModel>> {

        val isAdultEntriesDisabled = !metadataDataSource.isUserAdult()

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PREFETCH_ITEMS
            ),
            pagingSourceFactory = {
                animePagingSource.getAnimeSearch(
                    query = query,
                    filters = filters,
                    isAdultEntriesDisabled = isAdultEntriesDisabled
                )
            }
        ).flow
    }

    /**
     * Retrieves a paginated list of top anime, optionally filtered by type.
     *
     * @param type Type filter (e.g., TV, Movie).
     * @param itemsLimit Optional limit on total number of items to load.
     */
    suspend fun getTopAnime(
        type : AnimeTypeFilters?,
        itemsLimit : Int? = null
    ) : Flow<PagingData<AnimeBasicModel>> {

        val isAdultEntriesDisabled = !metadataDataSource.isUserAdult()

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PREFETCH_ITEMS
            ),
            pagingSourceFactory = {
                animePagingSource.getTopAnime(
                    type = type?.toLowerCase(),
                    isAdultEntriesDisabled = isAdultEntriesDisabled,
                    itemsLimit = itemsLimit
                )
            }
        ).flow
    }

    /**
     * Retrieves the currently airing season anime.
     *
     * @param type Type filter (optional).
     * @param itemsLimit Optional limit on items.
     */
    suspend fun getAnimeCurrentSeason(
        type : AnimeTypeFilters?,
        itemsLimit : Int? = null
    ) : Flow<PagingData<AnimeBasicModel>> {

        val isAdultEntriesDisabled = !metadataDataSource.isUserAdult()

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PREFETCH_ITEMS
            ),
            pagingSourceFactory = {
                animePagingSource.getAnimeCurrentSeason(
                    type = type?.toLowerCase(),
                    isAdultEntriesDisabled = isAdultEntriesDisabled,
                    itemsLimit = itemsLimit
                )
            }
        ).flow
    }

    /**
     * Retrieves anime from a specific [year] and [season].
     *
     * @param year Target year.
     * @param season Season of the year (e.g., WINTER).
     * @param type Optional type filter.
     */
    suspend fun getAnimeSeason(
        year : Int,
        season : Season,
        type : AnimeTypeFilters?
    ) : Flow<PagingData<AnimeBasicModel>> {

        val isAdultEntriesDisabled = !metadataDataSource.isUserAdult()

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PREFETCH_ITEMS
            ),
            pagingSourceFactory = {
                animePagingSource.getAnimeSeason(
                    year = year,
                    season = season.toLowerCase()!!,
                    type = type?.toLowerCase(),
                    isAdultEntriesDisabled = isAdultEntriesDisabled
                )
            }
        ).flow
    }

    /**
     * Retrieves anime from upcoming seasons.
     *
     * @param type Optional type filter.
     * @param itemsLimit Optional limit on items.
     */
    suspend fun getAnimeSeasonUpcoming(
        type : AnimeTypeFilters?,
        itemsLimit : Int? = null
    ) : Flow<PagingData<AnimeBasicModel>> {

        val isAdultEntriesDisabled = !metadataDataSource.isUserAdult()

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PREFETCH_ITEMS
            ),
            pagingSourceFactory = {
                animePagingSource.getAnimeSeasonUpcoming(
                    type = type?.toLowerCase(),
                    isAdultEntriesDisabled = isAdultEntriesDisabled,
                    itemsLimit = itemsLimit
                )
            }
        ).flow
    }

    /** Retrieves all years for which seasonal data exists in cache. */
    suspend fun getAnimeSeasonYears() : List<Int> = seasonDataSource.getAllYearsWithSeasons()

    /**
     * Retrieves all seasons available for a given [year].
     *
     * @param year Target year.
     * @return A list of [Season] enums.
     */
    suspend fun getAnimeSeasonsByYear(year : Int) : List<Season> {
        return seasonDataSource.getSeasonNamesByYear(year).map { Season.fromString(it)!! }
    }

    /**
     * Retrieves the list of anime genres from the local data source
     * and converts it into a domain-level model.
     *
     * @return A domain representation of the list of anime genres.
     */
    suspend fun getAnimeGenres() = genreDataSource.getGenres().toDomainListAnime()

    /**
     * Updates the locally cached list of anime seasons if the last update
     * exceeds the defined threshold ([MONTHS_FOR_SEASON_UPDATE]).
     *
     * @return A [DomainResult] indicating success or error.
     */
    suspend fun updateAnimeSeasons() : DomainResult<Unit> = RepositoryUtil.updateCacheWithNewData(
        shouldUpdate = metadataDataSource.shouldUpdateSeasons(MONTHS_FOR_SEASON_UPDATE),
        apiResponse = animeRepository.getAnimeSeasonList()
    ) { content ->
        val seasons = content.data.toCacheEntityList()
        val updated = seasonDataSource.updateSeasonsCache(seasons)
        Logger.i("AnimeRepository", "Seasons updated: $updated")
        metadataDataSource.updateSeasonsLastModifiedDate()
    }

    /**
     * Updates the locally cached list of anime genres if the last update
     * exceeds the defined threshold ([MONTHS_FOR_GENRES_UPDATE]).
     *
     * @return A [DomainResult] indicating success or error.
     */
    suspend fun updateAnimeGenres() : DomainResult<Unit> = RepositoryUtil.updateCacheWithNewData(
        shouldUpdate = metadataDataSource.shouldUpdateAnimeGenres(MONTHS_FOR_GENRES_UPDATE),
        apiResponse = animeRepository.getAnimeGenres()
    ) { content ->
        val genres = content.data.toAnimeGenreCacheList()
        val updated = genreDataSource.syncGenres(genres)
        Logger.i("AnimeRepository", "Anime genres updated: $updated")
        metadataDataSource.updateAnimeGenresLastModifiedDate()
    }


}

