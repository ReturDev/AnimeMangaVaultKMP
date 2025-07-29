package com.returdev.animemanga.data.remote.model.core.wrapper.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Generic data class representing a standard API response wrapper.
 *
 * @param T The type of the data contained in the response.
 * @property data The actual data returned by the API.
 */
@Serializable
data class DataResponse<T>(
    @SerialName("data") val data : T
)
