package com.returdev.animemanga.domain.model.core.search.manga

/**
 * Enum class representing the available status filters for manga search results.
 *
 * The filters include:
 * - PUBLISHING: Manga that are currently being published.
 * - COMPLETE: Manga that have finished publishing.
 * - HIATUS: Manga that are on hiatus.
 * - DISCONTINUED: Manga that have been discontinued.
 * - UPCOMING: Manga that are scheduled to be published in the future.
 */
enum class MangaStatusFilters {

    PUBLISHING,
    COMPLETE,
    HIATUS,
    DISCONTINUED,
    UPCOMING;

}