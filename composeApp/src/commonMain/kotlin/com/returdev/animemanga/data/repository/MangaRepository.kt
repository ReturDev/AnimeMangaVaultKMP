package com.returdev.animemanga.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.returdev.animemanga.core.Logger
import com.returdev.animemanga.data.cache.datasource.CacheMetadataDataSource
import com.returdev.animemanga.data.cache.datasource.genre.MangaGenreCacheDataSource
import com.returdev.animemanga.data.cache.model.extension.toDomainListManga
import com.returdev.animemanga.data.cache.model.extension.toMangaGenreCacheList
import com.returdev.animemanga.data.model.extension.toLowerCase
import com.returdev.animemanga.data.paging.GenericPagingSource
import com.returdev.animemanga.data.paging.MangaPagingSource
import com.returdev.animemanga.data.remote.model.core.extension.toDomainModel
import com.returdev.animemanga.data.remote.model.core.extension.toPagedDomainModel
import com.returdev.animemanga.data.remote.repository.MangaRemoteRepository
import com.returdev.animemanga.data.remote.service.ApiService
import com.returdev.animemanga.data.repository.core.RepositoryUtil
import com.returdev.animemanga.domain.model.core.result.DomainResult
import com.returdev.animemanga.domain.model.core.search.SearchFilters
import com.returdev.animemanga.domain.model.core.search.manga.MangaTypeFilters
import com.returdev.animemanga.domain.model.manga.MangaBasicModel
import com.returdev.animemanga.domain.model.manga.MangaModel
import kotlinx.coroutines.flow.Flow

/**
 * Repository responsible for fetching and managing Manga-related data.
 *
 * Acts as an intermediary between remote data sources ([MangaRemoteRepository]),
 * local caching ([MangaGenreCacheDataSource]), and UI-level domain models.
 *
 * @property mangaRepository Remote repository handling API calls to fetch manga data.
 * @property mangaPagingSource Paging source for loading paginated manga lists.
 * @property genreDataSource Local cache for manga genres.
 * @property metadataDataSource Cache for metadata, e.g., update timestamps and user settings.
 */
class MangaRepository(
    private val mangaRepository : MangaRemoteRepository,
    private val mangaPagingSource : MangaPagingSource,
    private val genreDataSource  : MangaGenreCacheDataSource,
    private val metadataDataSource : CacheMetadataDataSource
) {

    companion object {
        /** Interval (in months) before cached genre data is considered stale. */
        private const val MONTHS_FOR_GENRES_UPDATE = 2
    }

    /**
     * Fetch detailed information about a manga by its [id].
     *
     * @param id Unique identifier of the manga.
     * @return A [DomainResult] wrapping the [MangaModel].
     */
    suspend fun getMangaById(id : Int) : DomainResult<MangaModel> {
        return mangaRepository.getMangaById(id).toDomainModel { it.toPagedDomainModel() }
    }

    /**
     * Search manga with a query and optional filters.
     *
     * @param query Search text query.
     * @param filters Filters to refine the search (e.g., type, status, score).
     * @return A [Flow] of [PagingData] containing [MangaBasicModel] entries.
     */
    suspend fun getMangaSearch(
        query : String,
        filters : SearchFilters.MangaFilters
    ) : Flow<PagingData<MangaBasicModel>> {

        val isAdultEntriesDisabled = !metadataDataSource.isUserAdult()

        return Pager(
            config = GenericPagingSource.getPagingConfig(),
            pagingSourceFactory = {
                mangaPagingSource.getMangaSearch(
                    query = query,
                    filters = filters,
                    isAdultEntriesDisabled = isAdultEntriesDisabled
                )
            }
        ).flow
    }

    /**
     * Fetch a paginated list of top manga.
     *
     * @param type Optional type filter (e.g., manga, novel).
     * @param itemsLimit Optional limit for the number of items returned.
     * @return A [Flow] of [PagingData] containing [MangaBasicModel].
     */
    suspend fun getTopManga(
        type : MangaTypeFilters?,
        itemsLimit : Int? = null
    ) : Flow<PagingData<MangaBasicModel>> {

        val isAdultEntriesDisabled = !metadataDataSource.isUserAdult()

        return Pager(
            config = GenericPagingSource.getPagingConfig(),
            pagingSourceFactory = {
                mangaPagingSource.getTopManga(
                    type = type?.toLowerCase(),
                    isAdultEntriesDisabled = isAdultEntriesDisabled,
                    itemsLimit = itemsLimit
                )
            }
        ).flow
    }

    /**
     * Update the locally cached manga genres if needed.
     *
     * @return A [DomainResult] indicating success or error.
     */
    suspend fun updateMangaGenres() : DomainResult<Unit> = RepositoryUtil.updateCacheWithNewData(
        shouldUpdate = metadataDataSource.shouldUpdateMangaGenres(MONTHS_FOR_GENRES_UPDATE),
        apiResponse = mangaRepository.getMangaGenres()
    ) { content ->
        val genres = content.data.toMangaGenreCacheList()
        val updated = genreDataSource.syncGenres(genres)
        Logger.i("MangaRepository", "Manga genres updated: $updated")
        metadataDataSource.updateMangaGenresLastModifiedDate()
    }

    /**
     * Retrieve the list of cached manga genres from local storage.
     *
     * @return A domain-level list of genres (if available).
     */
    suspend fun getMangaGenres() = genreDataSource.getGenres().toDomainListManga()
}
