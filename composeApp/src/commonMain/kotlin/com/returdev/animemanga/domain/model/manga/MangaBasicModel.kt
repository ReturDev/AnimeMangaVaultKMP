package com.returdev.animemanga.domain.model.manga

import com.returdev.animemanga.domain.model.core.ImageType
import com.returdev.animemanga.domain.model.visualmedia.VisualMediaBasicModel

/**
 * Represents the basic domain model for a manga, containing essential information.
 *
 * Extends [VisualMediaBasicModel] to include core visual media properties
 *
 * @property id Unique identifier of the manga.
 * @property images A list of [ImageType] representing different image sizes for the manga.
 * @property title The main title of the manga.
 * @property type Optional type of the manga (e.g., Manga, Light Novel).
 * @property score The average user score or rating for the manga.
 */
data class MangaBasicModel(
    override val id: Int,
    override val images: List<ImageType>,
    override val title: String,
    override val type: String?,
    override val score: Float
) : VisualMediaBasicModel()
