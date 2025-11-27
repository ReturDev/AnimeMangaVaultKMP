package com.returdev.animemanga.domain.model.anime

/**
 * Represents the trailer information of a visual media item, such as an anime.
 *
 * @property videoUrl The URL of the trailer video. Can be `null` if no trailer is available.
 * @property thumbnailImageUrl The URL of the trailer thumbnail image. Can be `null` if no image is available.
 */
data class TrailerModel(
    val videoUrl : String,
    val thumbnailImageUrl : String,
)