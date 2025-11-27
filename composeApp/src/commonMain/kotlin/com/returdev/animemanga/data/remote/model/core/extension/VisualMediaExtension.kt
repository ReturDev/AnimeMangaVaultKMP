package com.returdev.animemanga.data.remote.model.core.extension

import com.returdev.animemanga.data.remote.model.anime.AnimeExtendedResponse
import com.returdev.animemanga.data.remote.model.anime.AnimeReducedResponse
import com.returdev.animemanga.data.remote.model.manga.MangaExtendedResponse
import com.returdev.animemanga.data.remote.model.manga.MangaReducedResponse
import com.returdev.animemanga.domain.model.anime.AnimeBasicModel
import com.returdev.animemanga.domain.model.anime.AnimeModel
import com.returdev.animemanga.domain.model.core.TitleModel
import com.returdev.animemanga.domain.model.manga.MangaBasicModel
import com.returdev.animemanga.domain.model.manga.MangaModel

/**
 * Converts an [AnimeExtendedResponse] to a detailed [AnimeModel] domain object.
 *
 * Maps all relevant fields including basic info, trailer, titles, scores,
 * rank, synopsis, genres, demographics, airing information, and season.
 *
 * @receiver The extended anime response from the API.
 * @return A fully mapped [AnimeModel].
 */
fun AnimeExtendedResponse.toPagedDomainModel() = AnimeModel(
    basicInfo = this.getBasicInfo(),
    trailer = trailer.toDomainModel(),
    extraTitles = titles.toTitleModelList(),
    numberOfScorers = numberOfScorers,
    rank = rank,
    synopsis = synopsis.orEmpty(),
    status = status,
    demographics = demographics.toDomainModelList(),
    genres = genres.toDomainModelList(),
    source = source,
    episodes = episodes,
    airing = airing,
    aired = aired.toDomainModel(),
    season = season
)

/**
 * Converts an [AnimeReducedResponse] to a basic [AnimeBasicModel] domain object.
 *
 * Used for list views, search results, or any context where only summary information is required.
 *
 * @receiver The reduced anime response from the API.
 * @return A [AnimeBasicModel] containing ID, title, images, type, and score.
 */
fun AnimeReducedResponse.toPagedDomainModel() = AnimeBasicModel(
    id = id,
    images = images.toDomainModelList(),
    title = title,
    type = type,
    score = score?: 0f
)

/**
 * Extracts the basic information from an [AnimeExtendedResponse] into [AnimeBasicModel].
 *
 * Picks the default title and converts image list to domain format.
 *
 * @receiver The extended anime response.
 * @return [AnimeBasicModel] with essential info only.
 */
fun AnimeExtendedResponse.getBasicInfo() = AnimeBasicModel(
    id = id,
    images = webpImages.toDomainModelList(),
    title = titles.toTitleModelList().first { it is TitleModel.DefaultTitle }.title,
    type = type,
    score = score
)

/**
 * Converts a [MangaReducedResponse] to a basic [MangaBasicModel] domain object.
 *
 * @receiver The reduced manga response from the API.
 * @return A [MangaBasicModel] containing ID, title, images, type, and score.
 */
fun MangaReducedResponse.toPagedDomainModel() = MangaBasicModel(
    id = id,
    images = images.toDomainModelList(),
    title = title,
    type = type,
    score = score?: 0f
)

/**
 * Converts a [MangaExtendedResponse] to a detailed [MangaModel] domain object.
 *
 * Maps all relevant fields including basic info, extra titles, scores, rank, synopsis,
 * demographics, genres, chapters, volumes, publishing status, and release information.
 *
 * @receiver The extended manga response from the API.
 * @return A fully mapped [MangaModel].
 */
fun MangaExtendedResponse.toPagedDomainModel() = MangaModel(
    basicInfo = this.getBasicInfo(),
    extraTitles = titles.toTitleModelList(),
    numberOfScorers = numberOfScorers,
    rank = rank,
    synopsis = synopsis.orEmpty(),
    status = status,
    demographics = demographics.toDomainModelList(),
    genres = genres.toDomainModelList(),
    chapters = chapters,
    volumes = volumes,
    isPublishing = publishing,
    published = published.toDomainModel()
)

/**
 * Extracts the basic information from a [MangaExtendedResponse] into [MangaBasicModel].
 *
 * Picks the default title and converts image list to domain format.
 *
 * @receiver The extended manga response.
 * @return [MangaBasicModel] with essential info only.
 */
fun MangaExtendedResponse.getBasicInfo() = MangaBasicModel(
    id = id,
    images = webpImages.toDomainModelList(),
    title = titles.toTitleModelList().first { it is TitleModel.DefaultTitle }.title,
    type = type,
    score = score
)
