package com.returdev.animemanga.data.remote.model.anime

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class representing trailer information for an anime.
 *
 * @property embeddedVideoUrl The URL for embedding the trailer video.
 * @property trailerImages An object containing URLs for trailer images in different sizes.
 */
@Serializable
data class AnimeTrailerInfoResponse(
    @SerialName("embed_url") val embeddedVideoUrl : String?,
    @SerialName("images") val trailerImages : TrailerImages
) {

    /**
     * Data class representing images related to the trailer in various sizes.
     *
     * @property mediumImageUrl The URL of the medium-sized trailer image.
     * @property largeImageUrl The URL of the large-sized trailer image.
     */
    @Serializable
    data class TrailerImages(
        @SerialName("image_url") val imageUrl : String?,
        @SerialName("medium_image_url") val mediumImageUrl : String?
    )

}