package com.returdev.animemanga.ui.model.extension

import com.returdev.animemanga.domain.model.anime.AnimeBasicModel
import com.returdev.animemanga.domain.model.core.ImageType
import com.returdev.animemanga.domain.model.manga.MangaBasicModel
import com.returdev.animemanga.ui.model.basic.AnimeBasicUi
import com.returdev.animemanga.ui.model.basic.MangaBasicUi
import com.returdev.animemanga.ui.model.core.AnimeType
import com.returdev.animemanga.ui.model.core.MangaType

fun AnimeBasicModel.toUi() : AnimeBasicUi = AnimeBasicUi(
    id = id,
    image = images.first { it is ImageType.NormalImage } as ImageType.NormalImage,
    title = title,
    type = AnimeType.fromString(type),
    score = score
)


fun MangaBasicModel.toUi() : MangaBasicUi = MangaBasicUi(
    id = id,
    image = images.first { it is ImageType.NormalImage } as ImageType.NormalImage,
    title = title,
    type = MangaType.fromString(type),
    score = score
)