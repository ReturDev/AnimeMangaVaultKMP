package com.returdev.animemanga.data.remote.model.search

import io.ktor.util.collections.StringMap

/**
 * Represents the base class for remote search filters.
 *
 * This sealed class is extended by specific search filter types
 * (e.g., Anime or Manga filters) and defines common filtering parameters
 * such as type, status, genres, sorting, and date ranges.
 */
sealed class RemoteSearchFilters {

    abstract val type: String?
    abstract val status: String?
    abstract val genreIds: String?
    abstract val orderBy: String?
    abstract val sort: String
    abstract val score : String
    abstract val publisherIds: String?
    abstract val startDate: String?
    abstract val endDate: String?

    /**
     * Search filters specific to anime.
     *
     * @property rating An optional rating filter (e.g., G, PG-13, R).
     */
    data class RemoteAnimeSearchFilters(
        override val type: String?,
        override val status: String?,
        override val genreIds: String?,
        override val orderBy: String?,
        override val sort: String,
        override val score : String,
        override val publisherIds: String?,
        override val startDate: String?,
        override val endDate: String?,
        val rating: String?
    ) : RemoteSearchFilters()

    /**
     * Search filters specific to manga.
     */
    data class RemoteMangaSearchFilters(
        override val type: String?,
        override val status: String?,
        override val genreIds: String?,
        override val orderBy: String?,
        override val sort: String,
        override val score : String,
        override val publisherIds: String?,
        override val startDate: String?,
        override val endDate: String?
    ) : RemoteSearchFilters()

}
