package com.returdev.animemanga.data.remote.model.core.extension

import com.returdev.animemanga.data.remote.model.anime.AnimeTrailerInfoResponse
import com.returdev.animemanga.data.remote.model.core.component.GenreDataComponentResponse
import com.returdev.animemanga.data.remote.model.core.component.ImageDataComponentResponse
import com.returdev.animemanga.data.remote.model.core.component.ReleasedDataComponentResponse
import com.returdev.animemanga.data.remote.model.core.component.TitleDataComponentResponse
import com.returdev.animemanga.domain.model.anime.TrailerModel
import com.returdev.animemanga.domain.model.core.GenreModel
import com.returdev.animemanga.domain.model.core.ImageType
import com.returdev.animemanga.domain.model.core.ReleasedModel
import com.returdev.animemanga.domain.model.core.TitleModel
import kotlinx.datetime.LocalDate

/**
 * Converts an [ImageDataComponentResponse] from the API to a list of [ImageType] domain models.
 *
 * Generates small, normal, and large image representations from the webp URLs provided.
 *
 * @receiver The API image component response.
 * @return A list of [ImageType] objects representing different sizes.
 */
fun ImageDataComponentResponse.toDomainModelList() : List<ImageType> {
    return with(this.webpFormat) {
        listOf(
            ImageType.SmallImage(smallImageUrl),
            ImageType.NormalImage(normalImageUrl),
            ImageType.LargeImage(largeImageUrl)
        )
    }
}

/**
 * Converts a [TitleDataComponentResponse] from the API to a [TitleModel].
 *
 * @receiver The title component response containing language and title info.
 * @return A [TitleModel] object if valid, or null if the title is not recognized.
 */
fun TitleDataComponentResponse.toTitleModel() : TitleModel? {
    return TitleModel.getTitle(this.language, this.title)
}

/**
 * Converts a list of [TitleDataComponentResponse]s into a list of [TitleModel]s,
 * ignoring null values.
 *
 * @receiver A list of API title components.
 * @return A list of valid [TitleModel] objects.
 */
fun List<TitleDataComponentResponse>.toTitleModelList() : List<TitleModel> {
    return this.mapNotNull { it.toTitleModel() }
}

/**
 * Converts an [AnimeTrailerInfoResponse] to a [TrailerModel] domain object.
 *
 * Extracts the YouTube video ID and constructs the video URL and thumbnail image.
 *
 * @receiver The API trailer response.
 * @return A [TrailerModel] if the video ID is valid, otherwise null.
 */
fun AnimeTrailerInfoResponse.toDomainModel() : TrailerModel? {
    val videoId = this.embeddedVideoUrl?.substringAfter("embed/")?.substringBefore("?")
    val videoUrl = videoId?.let { "https://www.youtube.com/watch?v=$it" }

    return videoUrl?.let {
        TrailerModel(
            videoUrl = videoUrl,
            thumbnailImageUrl = this.trailerImages.toImageUrl(videoId)
        )
    }
}

/**
 * Helper to get the best available thumbnail image URL for a YouTube trailer.
 *
 * @receiver Trailer image information from the API.
 * @param videoId The YouTube video ID extracted from the embedded URL.
 * @return The best thumbnail image URL available.
 */
fun AnimeTrailerInfoResponse.TrailerImages.toImageUrl(videoId : String) : String {
    var url = this.mediumImageUrl ?: this.imageUrl
    if (url == null) {
        url = "https://img.youtube.com/vi/$videoId/maxresdefault.jpg"
    }
    return url
}

/**
 * Converts a [ReleasedDataComponentResponse] to a [ReleasedModel] domain object.
 *
 * Maps start and end dates from the API to [LocalDate].
 *
 * @receiver The released component from the API.
 * @return A [ReleasedModel] with `from` and optional `to` dates.
 */
fun ReleasedDataComponentResponse.toDomainModel() : ReleasedModel {
    val startDate = this.date.startDate
    val endDate = this.date.endDate

    val from = LocalDate(
        year = startDate.year,
        month = startDate.month,
        day = startDate.day
    )

    val to = endDate?.let {
        LocalDate(
            year = it.year,
            month = it.month,
            day = it.day
        )
    }

    return ReleasedModel(
        from = from,
        to = to
    )
}

/**
 * Converts a [GenreDataComponentResponse] from the API to a [GenreModel] domain object.
 *
 * @receiver The genre component from the API.
 * @return A [GenreModel] with the same id and name.
 */
fun GenreDataComponentResponse.toDomainModel() : GenreModel {
    return GenreModel(
        id = this.id,
        name = this.name,
    )
}

/**
 * Converts a list of [GenreDataComponentResponse]s to a list of [GenreModel] domain objects.
 *
 * @receiver A list of genre components from the API.
 * @return A list of corresponding [GenreModel] objects.
 */
fun List<GenreDataComponentResponse>.toDomainModelList() : List<GenreModel> {
    return this.map { it.toDomainModel() }
}
