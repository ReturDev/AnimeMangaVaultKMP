package com.returdev.animemanga.data.remote.model.manga

import com.returdev.animemanga.data.remote.model.core.component.GenreDataComponentResponse
import com.returdev.animemanga.data.remote.model.core.component.ImageDataComponentResponse
import com.returdev.animemanga.data.remote.model.core.component.ReleasedDataComponentResponse
import com.returdev.animemanga.data.remote.model.core.component.TitleDataComponentResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents an extended response model for manga data from the remote API.
 *
 * @property id The unique identifier of the manga (MyAnimeList ID).
 * @property webpImages The image data of the manga in WebP format.
 * @property titles A list of title representations for the manga.
 * @property type The type of the manga (e.g., Manga, Novel, etc.), nullable.
 * @property chapters The number of chapters in the manga.
 * @property volumes The number of volumes in the manga.
 * @property score The average score or rating of the manga.
 * @property numberOfScorers The number of users who have scored the manga.
 * @property rank The ranking position of the manga.
 * @property synopsis The synopsis or description of the manga, nullable.
 * @property genres A list of genre data associated with the manga.
 * @property status The current publishing status of the manga.
 * @property demographics A list of demographic genres for the manga.
 * @property publishing Indicates if the manga is currently being published.
 * @property published The published date information of the manga.
 */
@Serializable
data class MangaExtendedResponse(
    @SerialName("mal_id") val id : Int,
    @SerialName("images") val webpImages : ImageDataComponentResponse,
    @SerialName("titles") val titles : List<TitleDataComponentResponse>,
    @SerialName("type") val type : String?,
    @SerialName("chapters") val chapters : Int,
    @SerialName("volumes") val volumes : Int,
    @SerialName("score") val score : Float,
    @SerialName("scored_by") val numberOfScorers : Long,
    @SerialName("rank") val rank : Int,
    @SerialName("synopsis") val synopsis : String?,
    @SerialName("genres") val genres : List<GenreDataComponentResponse>,
    @SerialName("status") val status : String,
    @SerialName("demographics") val demographics : List<GenreDataComponentResponse>,
    @SerialName("publishing") val publishing : Boolean,
    @SerialName("published") val published : ReleasedDataComponentResponse,
)