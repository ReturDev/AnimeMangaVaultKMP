package com.returdev.animemanga.data.remote.model.core.component

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class representing image data in WebP format for an anime or manga.
 *
 * @property webpFormat An object containing URLs for images in different sizes and WebP format.
 */
@Serializable
data class ImageDataComponentResponse(
    @SerialName("webp") val webpFormat : ImageUrls
) {

    /**
     * Data class containing URLs for images in various sizes.
     *
     * @property smallImageUrl The URL for the small-sized image.
     * @property normalImageUrl The URL for the normal-sized image.
     * @property largeImageUrl The URL for the large-sized image.
     */
    @Serializable
    data class ImageUrls(
        @SerialName("small_image_url") val smallImageUrl : String,
        @SerialName("image_url") val normalImageUrl : String,
        @SerialName("large_image_url") val largeImageUrl : String
    )
}