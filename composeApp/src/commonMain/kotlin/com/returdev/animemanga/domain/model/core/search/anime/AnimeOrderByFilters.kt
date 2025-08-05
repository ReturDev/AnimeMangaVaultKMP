package com.returdev.animemanga.domain.model.core.search.anime

/**
 * Enum class representing the available filters for ordering anime search results.
 *
 * The filters include:
 * - TITLE: Order by the anime's title.
 * - START_DATE: Order by the start date of the anime.
 * - END_DATE: Order by the end date of the anime.
 * - EPISODES: Order by the number of episodes.
 * - SCORE: Order by the anime's score.
 * - SCORED_BY: Order by the number of users who scored the anime.
 * - RANK: Order by the anime's ranking.
 * - POPULARITY: Order by the anime's popularity.
 * - FAVORITES: Order by the number of times the anime has been favorited.
 */
enum class AnimeOrderByFilters {
    TITLE,
    START_DATE,
    END_DATE,
    EPISODES,
    SCORE,
    SCORED_BY,
    RANK,
    POPULARITY,
    FAVORITES;
}