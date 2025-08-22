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
 * Converts an [ImageDataComponentResponse] to a list of [ImageType] domain models.
 *
 * Creates Small, Normal, and Large image representations from the WebP format URLs.
 *
 * @receiver The image component response from the API.
 * @return A list of [ImageType] representing different image sizes.
 */
fun ImageDataComponentResponse.toDomainModelList(): List<ImageType> {
    return with(this.webpFormat) {
        listOf(
            ImageType.SmallImage(smallImageUrl),
            ImageType.NormalImage(normalImageUrl),
            ImageType.LargeImage(largeImageUrl)
        )
    }
}

/**
 * Converts a [TitleDataComponentResponse] to a [TitleModel].
 *
 * @receiver The title component response containing a language and title string.
 * @return A [TitleModel] if valid, or null if the title is not recognized.
 */
fun TitleDataComponentResponse.toTitleModel(): TitleModel? {
    return TitleModel.getTitle(this.language, this.title)
}

/**
 * Converts a list of [TitleDataComponentResponse] into a list of [TitleModel]s,
 * ignoring any null values.
 *
 * @receiver A list of title component responses.
 * @return A list of valid [TitleModel] objects.
 */
fun List<TitleDataComponentResponse>.toTitleModelList(): List<TitleModel> {
    return this.mapNotNull { it.toTitleModel() }
}

/**
 * Converts an [AnimeTrailerInfoResponse] to a [TrailerModel] domain object.
 *
 * @receiver The API trailer response.
 * @return A [TrailerModel] containing video URLs and image previews.
 */
fun AnimeTrailerInfoResponse.toDomainModel() = TrailerModel(
    videoUrl = this.videoUrl,
    embeddedVideoUrl = this.embeddedVideoUrl,
    images = this.trailerImages.toImageTypeList()
)

/**
 * Converts [AnimeTrailerInfoResponse.TrailerImages] to a list of [ImageType]s.
 *
 * Only includes non-null medium and large images.
 *
 * @receiver The trailer images from the API.
 * @return A list of [ImageType] representing available trailer images.
 */
fun AnimeTrailerInfoResponse.TrailerImages.toImageTypeList(): List<ImageType> {
    return listOfNotNull(
        mediumImageUrl?.let { ImageType.NormalImage(it) },
        largeImageUrl?.let { ImageType.LargeImage(it) }
    )
}

/**
 * Converts a [ReleasedDataComponentResponse] to a [ReleasedModel] domain object.
 *
 * Maps start and end dates from API components into [LocalDate].
 *
 * @receiver The released data component from the API.
 * @return A [ReleasedModel] with `from` and optional `to` dates.
 */
fun ReleasedDataComponentResponse.toDomainModel(): ReleasedModel {
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
 * Converts a [GenreDataComponentResponse] to a [GenreModel] domain object.
 *
 * @receiver The genre component from the API.
 * @return A [GenreModel] with the same id and name.
 */
fun GenreDataComponentResponse.toDomainModel(): GenreModel {
    return GenreModel(
        id = this.id,
        name = this.name,
    )
}

/**
 * Converts a list of [GenreDataComponentResponse] into a list of [GenreModel]s.
 *
 * @receiver The list of genre components from the API.
 * @return A list of corresponding [GenreModel] objects.
 */
fun List<GenreDataComponentResponse>.toDomainModelList(): List<GenreModel> {
    return this.map { it.toDomainModel() }
}
