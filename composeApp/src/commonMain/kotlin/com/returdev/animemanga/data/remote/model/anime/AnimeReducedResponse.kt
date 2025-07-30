package com.returdev.animemanga.data.remote.model.anime

import com.returdev.animemanga.data.remote.model.core.component.ImageDataComponentResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class representing a reduced version of an anime response.
 *
 * @property id The MyAnimeList ID of the anime.
 * @property images The images associated with the anime.
 * @property title The title of the anime.
 * @property type The type of the anime (e.g., TV, Movie, OVA). Nullable.
 * @property score The score of the anime.
 */
@Serializable
data class AnimeReducedResponse(
    @SerialName("mal_id") val id : Int,
    @SerialName("images") val images : ImageDataComponentResponse,
    @SerialName("title") val title : String,
    @SerialName("type") val type : String?,
    @SerialName("score") val score : Float
)