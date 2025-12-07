package com.returdev.animemanga.domain.model.core.search

import com.returdev.animemanga.domain.model.core.search.anime.AnimeOrderByFilters
import com.returdev.animemanga.domain.model.core.search.anime.AnimeRatingFilters
import com.returdev.animemanga.domain.model.core.search.anime.AnimeStatusFilters
import com.returdev.animemanga.domain.model.core.search.anime.AnimeTypeFilters
import com.returdev.animemanga.domain.model.core.search.common.SortDirection
import com.returdev.animemanga.domain.model.core.search.manga.MangaOrderByFilters
import com.returdev.animemanga.domain.model.core.search.manga.MangaStatusFilters
import com.returdev.animemanga.domain.model.core.search.manga.MangaTypeFilters
import kotlinx.datetime.LocalDate

/**
 * Sealed class representing the base structure for search filters used in anime and manga queries.
 *
 * This class defines common properties for filtering search results:
 * @property type The type filter (e.g., anime or manga type). The specific enum type depends on the subclass.
 * @property status The status filter (e.g., publishing, complete). The specific enum type depends on the subclass.
 * @property score The minimum score to filter results.
 * @property genreIds List of genre IDs to filter the results.
 * @property orderBy The field by which to order the results. The specific enum type depends on the subclass.
 * @property sort The direction to sort the results (ascending or descending).
 * @property publisherIds List of publisher IDs to filter the results.
 * @property startDate The start date to filter results.
 * @property endDate The end date to filter results.
 *
 * Subclasses:
 * - AnimeFilters: Filters specific to anime searches.
 * - MangaFilters: Filters specific to manga searches.
 */
sealed class SearchFilters {

    abstract val type : Any?
    abstract val status : Any?
    abstract val score : Int?
    abstract val genreIds : List<Int>
    abstract val orderBy : Any?
    abstract val sort : SortDirection
    abstract val publisherIds : List<Int>
    abstract val startDate : LocalDate?
    abstract val endDate : LocalDate?

    /**
     * Data class for anime-specific search filters.
     *
     * @property type The type of anime (e.g., TV, Movie).
     * @property status The status of the anime (e.g., airing, completed).
     * @property genreIds List of genre IDs to filter anime.
     * @property orderBy The field to order anime results by.
     * @property sort The direction to sort anime results.
     * @property score The minimum score to filter anime.
     * @property publisherIds List of publisher IDs to filter anime.
     * @property startDate The start date to filter anime.
     * @property endDate The end date to filter anime.
     */
    data class AnimeFilters(
        override val type : AnimeTypeFilters? = null,
        override val status : AnimeStatusFilters? = null,
        override val genreIds : List<Int> = emptyList(),
        override val orderBy : AnimeOrderByFilters? = null,
        override val sort : SortDirection = SortDirection.ASCENDANT,
        override val score : Int? = null,
        override val publisherIds : List<Int> = emptyList(),
        override val startDate : LocalDate? = null,
        override val endDate : LocalDate? = null,
        val rating : AnimeRatingFilters? = null
    ) : SearchFilters()

    /**
     * Data class for manga-specific search filters.
     *
     * @property type The type of manga (e.g., Manga, Novel).
     * @property status The status of the manga (e.g., publishing, complete).
     * @property genreIds List of genre IDs to filter manga.
     * @property orderBy The field to order manga results by.
     * @property sort The direction to sort manga results.
     * @property score The minimum score to filter manga.
     * @property publisherIds List of publisher IDs to filter manga.
     * @property startDate The start date to filter manga.
     * @property endDate The end date to filter manga.
     */
    data class MangaFilters(
        override val type : MangaTypeFilters? = null,
        override val status : MangaStatusFilters? = null,
        override val genreIds : List<Int> = emptyList(),
        override val orderBy : MangaOrderByFilters? = null,
        override val sort : SortDirection = SortDirection.ASCENDANT,
        override val score : Int? = null,
        override val publisherIds : List<Int> = emptyList(),
        override val startDate : LocalDate? = null,
        override val endDate : LocalDate? = null
    ) : SearchFilters()
}