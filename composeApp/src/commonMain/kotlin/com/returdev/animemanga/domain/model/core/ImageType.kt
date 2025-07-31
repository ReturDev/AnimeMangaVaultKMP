package com.returdev.animemanga.domain.model.core

/**
 * Sealed class representing different types of images with associated URLs.
 *
 * @property url The URL of the image.
 */
sealed class ImageType {
    /**
     * The URL of the image.
     */
    abstract val url : String

    /**
     * Represents a small-sized image.
     *
     * @property url The URL of the small image.
     */
    data class SmallImage(override val url : String) : ImageType()

    /**
     * Represents a normal-sized image.
     *
     * @property url The URL of the normal image.
     */
    data class NormalImage(override val url : String) : ImageType()

    /**
     * Represents a large-sized image.
     *
     * @property url The URL of the large image.
     */
    data class LargeImage(override val url : String) : ImageType()

}