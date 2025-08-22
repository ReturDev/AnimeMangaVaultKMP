package com.returdev.animemanga.data.remote.model.core.extension

import com.returdev.animemanga.data.model.extension.toLowerCase
import com.returdev.animemanga.data.remote.model.search.RemoteSearchFilters
import com.returdev.animemanga.domain.model.core.search.SearchFilters
import com.returdev.animemanga.domain.model.core.search.anime.AnimeRatingFilters
import github.returdev.animemangavault.core.model.core.filters.common.SortDirection

/**
 * Converts domain-level [SearchFilters.AnimeFilters] into
 * [RemoteSearchFilters.RemoteAnimeSearchFilters] suitable for API requests.
 *
 * Maps enum values, rating filters, and genre/publisher lists into
 * the format expected by the remote API.
 *
 * @receiver The domain anime search filters.
 * @return A [RemoteSearchFilters.RemoteAnimeSearchFilters] object ready for remote API calls.
 */
fun SearchFilters.AnimeFilters.toRemoteSearchFilters(): RemoteSearchFilters.RemoteAnimeSearchFilters {
    return RemoteSearchFilters.RemoteAnimeSearchFilters(
        type = type?.toLowerCase(),
        status = status?.toLowerCase(),
        genreIds = genreIds.joinToString(","),
        orderBy = orderBy?.toLowerCase(),
        sort = sort.toRemoteSort(),
        score = score?.toString(),
        publisherIds = publisherIds.joinToString(","),
        startDate = null,
        endDate = null,
        rating = rating?.toRemoteRating()
    )
}

/**
 * Converts domain-level [SearchFilters.MangaFilters] into
 * [RemoteSearchFilters.RemoteMangaSearchFilters] suitable for API requests.
 *
 * Maps enum values, genre/publisher lists, and sort options into
 * the format expected by the remote API.
 *
 * @receiver The domain manga search filters.
 * @return A [RemoteSearchFilters.RemoteMangaSearchFilters] object ready for remote API calls.
 */
fun SearchFilters.MangaFilters.toRemoteSearchFilters(): RemoteSearchFilters.RemoteMangaSearchFilters {
    return RemoteSearchFilters.RemoteMangaSearchFilters(
        type = type?.toLowerCase(),
        status = status?.toLowerCase(),
        genreIds = genreIds.joinToString(","),
        orderBy = orderBy?.toLowerCase(),
        sort = sort.toRemoteSort(),
        score = score?.toString(),
        publisherIds = publisherIds.joinToString(","),
        startDate = null,
        endDate = null
    )
}

/**
 * Maps domain-level [SortDirection] into the remote API's expected sort string.
 *
 * @receiver The domain sort direction.
 * @return "asc" for ascending or "desc" for descending order.
 */
fun SortDirection.toRemoteSort(): String {
    return when (this) {
        SortDirection.ASCENDANT -> "asc"
        SortDirection.DESCENDANT -> "desc"
    }
}

/**
 * Maps domain-level [AnimeRatingFilters] into the remote API's expected rating string.
 *
 * @receiver The domain anime rating filter.
 * @return A string representing the rating in the format required by the API.
 */
fun AnimeRatingFilters.toRemoteRating(): String {
    return when (this) {
        AnimeRatingFilters.G -> "g"
        AnimeRatingFilters.PG -> "pg"
        AnimeRatingFilters.PG_13 -> "pg13"
        AnimeRatingFilters.R_17 -> "r17"
        AnimeRatingFilters.R_PLUS -> "r"
        AnimeRatingFilters.RX -> "rx"
    }
}
