package com.returdev.animemanga.data.remote.model.season

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents the response model for season data from the remote API.
 *
 * @property year The year associated with the seasons.
 * @property seasons A list of season names for the specified year.
 */
@Serializable
data class SeasonResponse(
    @SerialName("year") val year : Int,
    @SerialName("seasons") val seasons : List<String>
)
