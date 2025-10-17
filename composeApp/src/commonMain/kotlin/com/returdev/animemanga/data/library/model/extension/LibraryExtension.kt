package com.returdev.animemanga.data.library.model.extension

import com.returdev.animemanga.data.library.model.entity.LibraryAnimeEntity
import com.returdev.animemanga.data.library.model.entity.LibraryMangaEntity
import com.returdev.animemanga.domain.model.anime.AnimeBasicModel
import com.returdev.animemanga.domain.model.core.ImageType
import com.returdev.animemanga.domain.model.manga.MangaBasicModel

fun LibraryAnimeEntity.toDomain() : AnimeBasicModel {
    return AnimeBasicModel(
        id = this.id,
        images = listOf(ImageType.NormalImage(this.image)),
        title = this.title,
        type = this.type,
        score = this.score
    )
}

fun LibraryMangaEntity.toDomain() : MangaBasicModel {
    return MangaBasicModel(
        id = this.id,
        images = listOf(ImageType.NormalImage(this.image)),
        title = this.title,
        type = this.type,
        score = this.score
    )
}