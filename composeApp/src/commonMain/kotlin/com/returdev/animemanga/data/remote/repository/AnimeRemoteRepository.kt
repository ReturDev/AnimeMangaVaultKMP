package com.returdev.animemanga.data.remote.repository

import com.returdev.animemanga.data.remote.core.util.ApiRequestManager
import com.returdev.animemanga.data.remote.model.anime.AnimeExtendedResponse
import com.returdev.animemanga.data.remote.model.anime.AnimeReducedResponse
import com.returdev.animemanga.data.remote.model.core.component.GenreDataComponentResponse
import com.returdev.animemanga.data.remote.model.core.wrapper.data.DataResponse
import com.returdev.animemanga.data.remote.model.core.wrapper.data.PagedDataResponse
import com.returdev.animemanga.data.remote.model.core.wrapper.response.ApiResponse
import com.returdev.animemanga.data.remote.model.search.RemoteSearchFilters
import com.returdev.animemanga.data.remote.model.season.SeasonResponse
import com.returdev.animemanga.data.remote.service.ApiService
import io.ktor.util.reflect.typeInfo

/**
 * Repository responsible for fetching Anime-related data
 * from the remote API using [ApiService] and [ApiRequestManager].
 *
 * @property requestManager The request manager responsible for executing API calls
 *                          and handling response parsing and error management.
 * @property apiService The API service interface defining Anime-related endpoints.
 */
class AnimeRemoteRepository(
    private val requestManager : ApiRequestManager,
    private val apiService : ApiService
) {

    /** Type information for a single anime extended response. */
    private val animeExtendedTypeInfo = typeInfo<DataResponse<AnimeExtendedResponse>>()

    /** Type information for a paged list of reduced anime responses. */
    private val animeReducedTypeInfo = typeInfo<PagedDataResponse<AnimeReducedResponse>>()

    /**
     * Fetches detailed information about an anime by its [id].
     *
     * @param id The unique identifier of the anime.
     * @return [ApiResponse] containing [AnimeExtendedResponse] with full anime details.
     */
    suspend fun getAnimeById(id : Int) : ApiResponse<DataResponse<AnimeExtendedResponse>> {
        return requestManager.executeRequest(
            typeInfo = animeExtendedTypeInfo,
            request = { apiService.getAnimeById(id.toString()) }
        )
    }

    /**
     * Searches for anime using title, pagination, and custom filters.
     *
     * @param page The page number to fetch (starting from 1).
     * @param title An optional title or partial title to search for.
     * @param filters The set of filters applied to the search (e.g., type, genres, rating).
     * @param isAdultEntriesDisabled Whether adult (R-18) entries should be excluded.
     * @return [ApiResponse] containing a paged list of [AnimeReducedResponse].
     */
    suspend fun animeSearch(
        page : Int,
        title : String?,
        filters : RemoteSearchFilters.RemoteAnimeSearchFilters,
        isAdultEntriesDisabled : Boolean
    ) : ApiResponse<PagedDataResponse<AnimeReducedResponse>> {
        return requestManager.executeRequest(
            typeInfo = animeReducedTypeInfo,
            request = {
                apiService.animeSearch(
                    page = page.toString(),
                    limit = ApiService.MAX_REQUEST_LIMIT.toString(),
                    isAdultEntriesDisabled = isAdultEntriesDisabled,
                    title = title,
                    type = filters.type,
                    score = filters.score,
                    status = filters.status,
                    rating = filters.rating,
                    genres = filters.genreIds,
                    orderBy = filters.orderBy,
                    sort = filters.sort,
                    producers = filters.publisherIds,
                    startDate = filters.startDate,
                    endDate = filters.endDate
                )
            }
        )
    }

    /**
     * Fetches a paginated list of top anime.
     *
     * @param page The page number to fetch.
     * @param type Optional anime type filter (e.g., TV, Movie).
     * @param isAdultEntriesDisabled Whether adult (R-18) entries should be excluded.
     * @return [ApiResponse] containing a paged list of [AnimeReducedResponse].
     */
    suspend fun getTopAnime(
        page : Int,
        type : String?,
        isAdultEntriesDisabled : Boolean
    ) : ApiResponse<PagedDataResponse<AnimeReducedResponse>> {
        return requestManager.executeRequest(
            typeInfo = animeReducedTypeInfo,
            request = {
                apiService.getTopAnime(
                    page = page.toString(),
                    limit = ApiService.MAX_REQUEST_LIMIT.toString(),
                    isAdultEntriesDisabled = isAdultEntriesDisabled,
                    type = type
                )
            }
        )
    }

    /**
     * Fetches anime from the **current season**.
     *
     * @param page The page number to fetch.
     * @param isAdultEntriesDisabled Whether adult (R-18) entries should be excluded.
     * @param type Optional anime type filter (e.g., TV, Movie).
     * @return [ApiResponse] containing a paged list of [AnimeReducedResponse].
     */
    suspend fun getAnimeCurrentSeason(
        page : Int,
        isAdultEntriesDisabled : Boolean,
        type : String?
    ) : ApiResponse<PagedDataResponse<AnimeReducedResponse>> {
        return requestManager.executeRequest(
            typeInfo = animeReducedTypeInfo,
            request = {
                apiService.getAnimeCurrentSeason(
                    page = page.toString(),
                    limit = ApiService.MAX_REQUEST_LIMIT.toString(),
                    isAdultEntriesDisabled = isAdultEntriesDisabled,
                    type = type
                )
            }
        )
    }

    /**
     * Fetches anime from a specific [season] in a given [year].
     *
     * @param year The release year of the season.
     * @param season The season (e.g., "winter", "spring", "summer", "fall").
     * @param page The page number to fetch.
     * @param isAdultEntriesDisabled Whether adult (R-18) entries should be excluded.
     * @param type Optional anime type filter (e.g., TV, Movie).
     * @return [ApiResponse] containing a paged list of [AnimeReducedResponse].
     */
    suspend fun getAnimeSeason(
        year : Int,
        season : String,
        page : Int,
        isAdultEntriesDisabled : Boolean,
        type : String?
    ) : ApiResponse<PagedDataResponse<AnimeReducedResponse>> {
        return requestManager.executeRequest(
            typeInfo = animeReducedTypeInfo,
            request = {
                apiService.getAnimeSeason(
                    year = year.toString(),
                    season = season,
                    page = page.toString(),
                    limit = ApiService.MAX_REQUEST_LIMIT.toString(),
                    isAdultEntriesDisabled = isAdultEntriesDisabled,
                    type = type
                )
            }
        )
    }

    /**
     * Fetches a list of **upcoming anime seasons**.
     *
     * @param page The page number to fetch.
     * @param isAdultEntriesDisabled Whether adult (R-18) entries should be excluded.
     * @param type Optional anime type filter (e.g., TV, Movie).
     * @return [ApiResponse] containing a paged list of [AnimeReducedResponse].
     */
    suspend fun getAnimeSeasonUpcoming(
        page : Int,
        isAdultEntriesDisabled : Boolean,
        type : String?
    ) : ApiResponse<PagedDataResponse<AnimeReducedResponse>> {
        return requestManager.executeRequest(
            typeInfo = animeReducedTypeInfo,
            request = {
                apiService.getAnimeSeasonUpcoming(
                    page = page.toString(),
                    limit = ApiService.MAX_REQUEST_LIMIT.toString(),
                    isAdultEntriesDisabled = isAdultEntriesDisabled,
                    type = type
                )
            }
        )
    }

    /**
     * Fetches the list of all available anime seasons (year and season pairs).
     *
     * @return [ApiResponse] containing a list of [SeasonResponse].
     */
    suspend fun getAnimeSeasonList() : ApiResponse<DataResponse<List<SeasonResponse>>> {
        return requestManager.executeRequest(
            typeInfo = typeInfo<List<SeasonResponse>>(),
            request = { apiService.getAnimeSeasonsList() }
        )
    }

    /**
     * Fetches the list of available anime genres.
     *
     * @return [ApiResponse] containing a list of [GenreDataComponentResponse].
     */
    suspend fun getAnimeGenres() : ApiResponse<DataResponse<List<GenreDataComponentResponse>>> {
        return requestManager.executeRequest(
            typeInfo = typeInfo<List<GenreDataComponentResponse>>(),
            request = { apiService.getAnimeGenres() }
        )
    }
}
