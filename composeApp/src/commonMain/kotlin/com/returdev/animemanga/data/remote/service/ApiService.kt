package com.returdev.animemanga.data.remote.service

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.util.StringValues

/**
 * Service class for making remote API requests related to anime and manga.
 * Uses Ktor's HttpClient to perform HTTP GET requests.
 *
 * @property httpClient The Ktor HttpClient instance used for requests.
 */
class ApiService(private val httpClient : HttpClient) {

    /**
     * Fetches anime details by its ID.
     *
     * @param animeId The unique identifier of the anime.
     * @return HttpResponse containing the anime data.
     */
    suspend fun getAnimeById(animeId : String) : HttpResponse {
        return getResponse("/anime/$animeId")
    }

    /**
     * Fetches manga details by its ID.
     *
     * @param mangaId The unique identifier of the manga.
     * @return HttpResponse containing the manga data.
     */
    suspend fun getMangaById(mangaId : String) : HttpResponse {
        return getResponse("/manga/$mangaId")
    }

    /**
     * Searches for anime with various filters.
     *
     * @param page The page number for pagination.
     * @param limit The number of results per page.
     * @param title The title or query string for anime search.
     * @param type The type of anime.
     * @param score The minimum score filter.
     * @param status The status of the anime.
     * @param rating The rating filter.
     * @param genres The genres filter.
     * @param orderBy The field to order results by.
     * @param sort The sort direction.
     * @param producers The producers filter.
     * @param startDate The start date filter.
     * @param endDate The end date filter.
     * @return HttpResponse containing the search results.
     */
    suspend fun animeSearch(
        page : String,
        limit : String,
        title : String?,
        type : String?,
        score : String?,
        status : String?,
        rating : String?,
        genres : String?,
        orderBy : String?,
        sort : String?,
        producers : String?,
        startDate : String?,
        endDate : String?
    ) : HttpResponse {
        return getResponse(
            urlString = "/anime",
            parameters = StringValues.Companion.build {
                append("page", page)
                append("limit", limit)
                title?.let { append("q", it) }
                type?.let { append("type", it) }
                score?.let { append("score", it) }
                status?.let { append("status", it) }
                rating?.let { append("rating", it) }
                genres?.let { append("genres", it) }
                orderBy?.let { append("order_by", it) }
                sort?.let { append("sort", it) }
                producers?.let { append("producers", it) }
                startDate?.let { append("start_date", it) }
                endDate?.let { append("end_date", it) }
            }
        )
    }

    /**
     * Searches for manga with various filters.
     *
     * @param page The page number for pagination.
     * @param limit The number of results per page.
     * @param title The title or query string for manga search.
     * @param type The type of manga.
     * @param score The minimum score filter.
     * @param status The status of the manga.
     * @param genres The genres filter.
     * @param orderBy The field to order results by.
     * @param sort The sort direction.
     * @param magazines The magazines filter.
     * @param startDate The start date filter.
     * @param endDate The end date filter.
     * @return HttpResponse containing the search results.
     */
    suspend fun mangaSearch(
        page : String,
        limit : String,
        title : String?,
        type : String?,
        score : String?,
        status : String?,
        genres : String?,
        orderBy : String?,
        sort : String?,
        magazines : String?,
        startDate : String?,
        endDate : String?
    ) : HttpResponse {
        return getResponse(
            urlString = "/manga",
            parameters = StringValues.Companion.build {
                append("page", page)
                append("limit", limit)
                title?.let { append("q", it) }
                type?.let { append("type", it) }
                score?.let { append("score", it) }
                status?.let { append("status", it) }
                genres?.let { append("genres", it) }
                orderBy?.let { append("order_by", it) }
                sort?.let { append("sort", it) }
                magazines?.let { append("magazines", it) }
                startDate?.let { append("start_date", it) }
                endDate?.let { append("end_date", it) }
            }
        )
    }

    /**
     * Fetches the top anime list.
     *
     * @param page The page number for pagination.
     * @param limit The number of results per page.
     * @param type The type of anime.
     * @return HttpResponse containing the top anime list.
     */
    suspend fun getTopAnime(
        page : String,
        limit : String,
        type : String?
    ) : HttpResponse {
        return getResponse(
            urlString = "/top/anime",
            parameters = StringValues.Companion.build {
                append("page", page)
                append("limit", limit)
                type?.let { append("type", it) }
            }
        )
    }

    /**
     * Fetches the top manga list.
     *
     * @param page The page number for pagination.
     * @param limit The number of results per page.
     * @param type The type of manga.
     * @return HttpResponse containing the top manga list.
     */
    suspend fun getTopManga(
        page : String,
        limit : String,
        type : String?
    ) : HttpResponse {
        return getResponse(
            urlString = "/top/manga",
            parameters = StringValues.Companion.build {
                append("page", page)
                append("limit", limit)
                type?.let { append("type", it) }
            }
        )
    }

    /**
     * Fetches anime for the current season.
     *
     * @param page The page number for pagination.
     * @param limit The number of results per page.
     * @param type The type of anime.
     * @return HttpResponse containing the current season anime.
     */
    suspend fun getAnimeCurrentSeason(
        page : String,
        limit : String,
        type : String?
    ) : HttpResponse {
        return getResponse(
            urlString = "/seasons/now",
            parameters = StringValues.Companion.build {
                append("page", page)
                append("limit", limit)
                type?.let { append("type", it) }
            }
        )
    }

    /**
     * Fetches anime for a specific season and year.
     *
     * @param year The year of the season.
     * @param season The season name (e.g., spring, summer).
     * @param page The page number for pagination.
     * @param limit The number of results per page.
     * @param type The type of anime.
     * @return HttpResponse containing the anime for the specified season.
     */
    suspend fun getAnimeSeason(
        year : String,
        season : String,
        page : String,
        limit : String,
        type : String?
    ) : HttpResponse {
        return getResponse(
            urlString = "/seasons/$year/$season",
            parameters = StringValues.Companion.build {
                append("page", page)
                append("limit", limit)
                type?.let { append("type", it) }
            }
        )
    }

    /**
     * Fetches the list of available anime seasons.
     *
     * @return HttpResponse containing the seasons list.
     */
    suspend fun getAnimeSeasonsList() : HttpResponse {
        return getResponse(
            urlString = "/seasons"
        )
    }

    /**
     * Fetches upcoming anime for the next season.
     *
     * @param page The page number for pagination.
     * @param limit The number of results per page.
     * @param type The type of anime.
     * @return HttpResponse containing the upcoming season anime.
     */
    suspend fun getAnimeSeasonUpcoming(
        page : String,
        limit : String,
        type : String?
    ) : HttpResponse {
        return getResponse(
            urlString = "/seasons/upcoming",
            parameters = StringValues.Companion.build {
                append("page", page)
                append("limit", limit)
                type?.let { append("type", it) }
            }
        )
    }

/**
 * Fetches the list of available anime genres.
 *
 * @return HttpResponse containing the anime genres data.
 */
suspend fun getAnimeGenres() : HttpResponse {
    return getResponse(
        urlString = "/genres/anime"
    )
}

/**
 * Fetches the list of available manga genres.
 *
 * @return HttpResponse containing the manga genres data.
 */
suspend fun getMangaGenres() : HttpResponse {
    return getResponse(
        urlString = "/genres/manga"
    )
}

    /**
     * Internal helper to perform a GET request with optional query parameters.
     *
     * @param urlString The endpoint URL string.
     * @param parameters Optional query parameters.
     * @return HttpResponse containing the response data.
     */
    private suspend fun getResponse(
        urlString : String,
        parameters : StringValues? = null
    ) : HttpResponse {
        return httpClient.get(urlString = urlString) {
            url {
                parameters?.let {
                    this.parameters.appendAll(it)
                }
            }
        }.call.response
    }

}