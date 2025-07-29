package com.returdev.animemanga.data.remote.model.core.component

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class representing a title component in the API response.
 *
 * @property language The language or type of the title (e.g., English, Japanese, etc.).
 * @property title The actual title string.
 */
@Serializable
data class TitleDataComponentResponse(
    @SerialName("type") val language : String,
    @SerialName("title") val title : String
)