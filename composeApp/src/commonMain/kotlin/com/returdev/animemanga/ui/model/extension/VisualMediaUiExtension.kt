package com.returdev.animemanga.ui.model.extension

import com.returdev.animemanga.domain.model.anime.AnimeBasicModel
import com.returdev.animemanga.domain.model.anime.AnimeModel
import com.returdev.animemanga.domain.model.core.ImageType
import com.returdev.animemanga.domain.model.core.ReleasedModel
import com.returdev.animemanga.domain.model.core.TitleModel
import com.returdev.animemanga.domain.model.manga.MangaBasicModel
import com.returdev.animemanga.domain.model.manga.MangaModel
import com.returdev.animemanga.ui.model.basic.AnimeBasicUi
import com.returdev.animemanga.ui.model.basic.MangaBasicUi
import com.returdev.animemanga.ui.model.core.AnimeType
import com.returdev.animemanga.ui.model.core.MangaType
import com.returdev.animemanga.ui.model.core.ReleasedUi
import com.returdev.animemanga.ui.model.core.TitleUi
import com.returdev.animemanga.ui.model.detailed.AnimeDetailedUi
import com.returdev.animemanga.ui.model.detailed.MangaDetailedUi

/**
 * Converts an [AnimeModel] into an [AnimeDetailedUi] for detailed screen rendering.
 *
 * Maps nested domain models such as titles and release information
 * and delegates basic info mapping to [AnimeBasicModel.toUi].
 *
 * @receiver The domain model containing full anime details.
 * @return A UI-ready detailed anime model.
 */
fun AnimeModel.toUi() : AnimeDetailedUi = AnimeDetailedUi(
    basicInfo = this.basicInfo.toUi(),
    trailer = this.trailer,
    extraTitles = this.extraTitles.map { it.toUi() },
    numberOfScorers = this.numberOfScorers,
    rank = this.rank,
    synopsis = this.synopsis,
    status = this.status,
    demographics = this.demographics,
    genres = this.genres,
    source = this.source,
    episodes = this.episodes,
    airing = this.airing,
    aired = this.aired.toUi(),
    season = this.season
)

/**
 * Converts an [AnimeBasicModel] into an [AnimeBasicUi].
 *
 * Selects the first available [ImageType.NormalImage] for display purposes.
 *
 * @receiver The domain model representing basic anime data.
 * @return A UI model used for rendering anime items.
 */
fun AnimeBasicModel.toUi() : AnimeBasicUi = AnimeBasicUi(
    id = id,
    image = images.first { it is ImageType.NormalImage } as ImageType.NormalImage,
    title = title,
    type = AnimeType.fromString(type),
    score = score
)

/**
 * Converts an [AnimeBasicUi] back into an [AnimeBasicModel].
 *
 * Wraps the displayed image into a list to match the domain model format.
 *
 * @receiver The UI model representing basic anime information.
 * @return The domain version of this model.
 */
fun AnimeBasicUi.toDomain() = AnimeBasicModel(
    id = this.id,
    images = listOf(this.image),
    title = this.title,
    type = this.type.name,
    score = this.score
)

/**
 * Converts a [MangaBasicModel] into a [MangaBasicUi].
 *
 * Selects the first available [ImageType.NormalImage] to represent the manga visually.
 *
 * @receiver The domain model representing basic manga data.
 * @return A UI model used for rendering manga items.
 */
fun MangaBasicModel.toUi() : MangaBasicUi = MangaBasicUi(
    id = id,
    image = images.first { it is ImageType.NormalImage } as ImageType.NormalImage,
    title = title,
    type = MangaType.fromString(type),
    score = score
)

/**
 * Converts a [MangaBasicUi] back into a [MangaBasicModel].
 *
 * Wraps the displayed image into a list to match domain requirements.
 *
 * @receiver The UI model containing basic manga info.
 * @return The equivalent domain model.
 */
fun MangaBasicUi.toDomain() = MangaBasicModel(
    id = this.id,
    images = listOf(this.image),
    title = this.title,
    type = this.type.name,
    score = this.score
)

/**
 * Converts a [MangaModel] into a [MangaDetailedUi] for detailed UI presentation.
 *
 * Maps nested domain fields such as titles and release data,
 * and converts the basic model using [MangaBasicModel.toUi].
 *
 * @receiver The full domain object for a manga entry.
 * @return A UI model containing all necessary manga details.
 */
fun MangaModel.toUi() : MangaDetailedUi = MangaDetailedUi(
    this.basicInfo.toUi(),
    this.extraTitles.map { it.toUi() },
    this.numberOfScorers,
    this.rank,
    this.synopsis,
    this.status,
    this.demographics,
    this.genres,
    this.chapters,
    this.volumes,
    this.isPublishing,
    this.published.toUi()
)

/**
 * Converts a [TitleModel] into a [TitleUi].
 *
 * Copies both the title type and localized/alternate title value
 * without transformation.
 *
 * @receiver A domain title representation.
 * @return A UI-friendly title model.
 */
fun TitleModel.toUi() = TitleUi(
    titleType = this.titleType,
    title = this.title
)

/**
 * Converts a [ReleasedModel] into a [ReleasedUi].
 *
 * Used for displaying start and end publication or airing dates.
 *
 * @receiver A domain model containing release date information.
 * @return A UI model with identical date structure.
 */
fun ReleasedModel.toUi() = ReleasedUi(
    from = this.from,
    to = this.to
)