package com.returdev.animemanga.data.cache.model.extension

import com.returdev.animemanga.data.cache.model.entity.SeasonCacheEntity
import com.returdev.animemanga.data.cache.model.entity.genre.AnimeGenreCacheEntity
import com.returdev.animemanga.data.cache.model.entity.genre.MangaGenreCacheEntity
import com.returdev.animemanga.data.remote.model.core.component.GenreDataComponentResponse
import com.returdev.animemanga.data.remote.model.season.SeasonResponse
import com.returdev.animemanga.domain.model.core.GenreModel

fun SeasonResponse.toCacheEntity() : SeasonCacheEntity {
    return SeasonCacheEntity(
        year = this.year,
        season = this.seasons.joinToString(",")
    )
}

fun List<SeasonResponse>.toCacheEntityList() : List<SeasonCacheEntity> {
    return this.map { it.toCacheEntity() }
}

fun List<GenreDataComponentResponse>.toAnimeGenreCacheList() : List<AnimeGenreCacheEntity> {
    return this.map { genre ->
        AnimeGenreCacheEntity(
            id = genre.id,
            name = genre.name,
        )
    }
}
fun List<GenreDataComponentResponse>.toMangaGenreCacheList() : List<MangaGenreCacheEntity> {
    return this.map { genre ->
        MangaGenreCacheEntity(
            id = genre.id,
            name = genre.name,
        )
    }
}

fun List<MangaGenreCacheEntity>.toDomainListManga() : List<GenreModel> {
    return this.map { genre ->
        GenreModel(
            id = genre.id,
            name = genre.name,
        )
    }
}

fun List<AnimeGenreCacheEntity>.toDomainListAnime() : List<GenreModel> {
    return this.map { genre ->
        GenreModel(
            id = genre.id,
            name = genre.name,
        )
    }
}