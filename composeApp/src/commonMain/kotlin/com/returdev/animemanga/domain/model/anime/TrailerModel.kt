package com.returdev.animemanga.domain.model.anime

import com.returdev.animemanga.data.remote.model.core.component.ImageDataComponentResponse
import com.returdev.animemanga.domain.model.core.ImageType

/**
 * Data class representing the trailer information of an anime.
 *
 * @property videoUrl The direct URL to the trailer video. Nullable.
 * @property embeddedVideoUrl The URL for the embedded trailer video (e.g., YouTube embed). Nullable.
 * @property images A list of image URLs related to the trailer.
 */
data class TrailerModel(
    val videoUrl : String?,
    val embeddedVideoUrl : String?,
    val images : List<ImageType>
)