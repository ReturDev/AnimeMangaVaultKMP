package com.returdev.animemanga.data.remote.repository

import com.returdev.animemanga.data.remote.core.util.ApiRequestManager
import com.returdev.animemanga.data.remote.model.core.component.GenreDataComponentResponse
import com.returdev.animemanga.data.remote.model.core.wrapper.data.DataResponse
import com.returdev.animemanga.data.remote.model.core.wrapper.data.PagedDataResponse
import com.returdev.animemanga.data.remote.model.core.wrapper.response.ApiResponse
import com.returdev.animemanga.data.remote.model.manga.MangaExtendedResponse
import com.returdev.animemanga.data.remote.model.manga.MangaReducedResponse
import com.returdev.animemanga.data.remote.model.search.RemoteSearchFilters
import com.returdev.animemanga.data.remote.service.ApiService
import io.ktor.util.reflect.typeInfo

/**
 * Repository responsible for fetching Manga-related data
 * from the remote API using [ApiService] and [ApiRequestManager].
 *
 * @property requestManager The request manager responsible for executing API calls
 *                          and handling response parsing and error handling.
 * @property apiService The API service interface defining Manga-related endpoints.
 */
class MangaRemoteRepository(
    private val requestManager: ApiRequestManager,
    private val apiService: ApiService
) {

    /** Type information for a single manga extended response. */
    private val mangaExtendedTypeInfo = typeInfo<DataResponse<MangaExtendedResponse>>()

    /** Type information for a paged list of reduced manga responses. */
    private val mangaReducedTypeInfo = typeInfo<PagedDataResponse<MangaReducedResponse>>()

    /**
     * Fetches detailed information about a manga by its [id].
     *
     * @param id The unique identifier of the manga.
     * @return [ApiResponse] containing [MangaExtendedResponse] with detailed manga data.
     */
    suspend fun getMangaById(id: Int): ApiResponse<DataResponse<MangaExtendedResponse>> {
        return requestManager.executeRequest(
            typeInfo = mangaExtendedTypeInfo,
            request = { apiService.getMangaById(id.toString()) }
        )
    }

    /**
     * Searches for manga using title, pagination, and additional filters.
     *
     * @param page The page number to fetch (starting from 1).
     * @param title An optional title or partial title to search for.
     * @param isAdultEntriesDisabled Whether adult (R-18) entries should be excluded.
     * @param type Optional manga type filter (e.g., Manga, Light Novel, One-shot).
     * @param filters The set of filters applied to the search (e.g., genres, status, dates).
     * @return [ApiResponse] containing a paged list of reduced manga results.
     */
    suspend fun mangaSearch(
        page: Int,
        title: String?,
        isAdultEntriesDisabled: Boolean,
        type: String?,
        filters: RemoteSearchFilters.RemoteMangaSearchFilters
    ): ApiResponse<PagedDataResponse<MangaReducedResponse>> {
        return requestManager.executeRequest(
            typeInfo = mangaReducedTypeInfo,
            request = {
                apiService.mangaSearch(
                    page = page.toString(),
                    limit = ApiService.MAX_REQUEST_LIMIT.toString(),
                    isAdultEntriesDisabled = isAdultEntriesDisabled,
                    title = title,
                    type = type,
                    score = filters.score,
                    status = filters.status,
                    genres = filters.genreIds,
                    orderBy = filters.orderBy,
                    sort = filters.sort,
                    magazines = filters.publisherIds,
                    startDate = filters.startDate,
                    endDate = filters.endDate
                )
            }
        )
    }

    /**
     * Fetches a paginated list of top manga.
     *
     * @param page The page number to fetch.
     * @param isAdultEntriesDisabled Whether adult (R-18) entries should be excluded.
     * @param type Optional manga type filter (e.g., Manga, Light Novel).
     * @return [ApiResponse] containing a paged list of [MangaReducedResponse].
     */
    suspend fun getTopManga(
        page: Int,
        isAdultEntriesDisabled: Boolean,
        type: String?
    ): ApiResponse<PagedDataResponse<MangaReducedResponse>> {
        return requestManager.executeRequest(
            typeInfo = mangaReducedTypeInfo,
            request = {
                apiService.getTopManga(
                    page = page.toString(),
                    limit = ApiService.MAX_REQUEST_LIMIT.toString(),
                    isAdultEntriesDisabled = isAdultEntriesDisabled,
                    type = type
                )
            }
        )
    }

    /**
     * Fetches the list of available manga genres.
     *
     * @return [ApiResponse] containing a list of [GenreDataComponentResponse].
     */
    suspend fun getMangaGenres(): ApiResponse<DataResponse<List<GenreDataComponentResponse>>> {
        return requestManager.executeRequest(
            typeInfo = typeInfo<DataResponse<List<GenreDataComponentResponse>>>(),
            request = { apiService.getMangaGenres() }
        )
    }
}
