package com.returdev.animemanga.data.remote.model.manga

import com.returdev.animemanga.data.remote.model.core.component.ImageDataComponentResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class representing a reduced version of an manga response.
 *
 * @property id The unique identifier of the manga (MyAnimeList ID).
 * @property images The image data associated with the manga.
 * @property title The title of the manga.
 * @property type The type of the manga (e.g., Manga, Novel, etc.), nullable.
 * @property score The score or rating of the manga.
 */
@Serializable
data class MangaReducedResponse(
    @SerialName("mal_id") val id : Int,
    @SerialName("images") val images : ImageDataComponentResponse,
    @SerialName("title") val title : String,
    @SerialName("type") val type : String?,
    @SerialName("score") val score : Float?
)