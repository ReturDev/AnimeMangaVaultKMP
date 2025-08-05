package com.returdev.animemanga.domain.model.core.search.manga

/**
 * Enum class representing the available type filters for manga search results.
 *
 * The filters include:
 * - MANGA: Standard Japanese comics.
 * - NOVEL: Written novels.
 * - LIGHT_NOVEL: Light novels, typically with illustrations.
 * - ONESHOT: Single-chapter manga.
 * - DOUJIN: Doujinshi, self-published works.
 * - MANHWA: Korean comics.
 * - MANHUA: Chinese comics.
 */
enum class MangaTypeFilters {

    MANGA,
    NOVEL,
    LIGHT_NOVEL,
    ONESHOT,
    DOUJIN,
    MANHWA,
    MANHUA;

}