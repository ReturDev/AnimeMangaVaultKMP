package com.returdev.animemanga.data.remote.model.core.component

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class representing a genre component in the API response.
 *
 * @property id The unique MyAnimeList identifier for the genre.
 */
@Serializable
data class GenreDataComponentResponse(
    @SerialName("mal_id") val id : Int
)