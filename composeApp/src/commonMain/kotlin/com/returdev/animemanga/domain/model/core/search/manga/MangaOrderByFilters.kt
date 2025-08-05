package com.returdev.animemanga.domain.model.core.search.manga

/**
 * Enum class representing the available filters for ordering manga search results.
 *
 * The filters include:
 * - TITLE: Order by the manga's title.
 * - START_DATE: Order by the start date of the manga.
 * - END_DATE: Order by the end date of the manga.
 * - CHAPTERS: Order by the number of chapters.
 * - VOLUMES: Order by the number of volumes.
 * - SCORE: Order by the manga's score.
 * - SCORED_BY: Order by the number of users who scored the manga.
 * - RANK: Order by the manga's ranking.
 * - POPULARITY: Order by the manga's popularity.
 * - FAVORITES: Order by the number of times the manga has been favorited.
 */
enum class MangaOrderByFilters {

    TITLE,
    START_DATE,
    END_DATE,
    CHAPTERS,
    VOLUMES,
    SCORE,
    SCORED_BY,
    RANK,
    POPULARITY,
    FAVORITES;

}