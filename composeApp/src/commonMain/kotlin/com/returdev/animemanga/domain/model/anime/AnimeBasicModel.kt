package com.returdev.animemanga.domain.model.anime

import com.returdev.animemanga.domain.model.core.ImageType
import com.returdev.animemanga.domain.model.visualmedia.VisualMediaBasicModel

/**
 * Data class representing the basic information of an anime.
 *
 * @property id The unique identifier of the anime.
 * @property images A list of images associated with the anime.
 * @property title The title of the anime.
 * @property type The type of the anime (e.g., TV, Movie, OVA). Nullable.
 * @property score The score or rating of the anime.
 */
data class AnimeBasicModel(
    override val id : Int,
    override val images : List<ImageType>,
    override val title : String,
    override val type : String?,
    override val score : Float
) : VisualMediaBasicModel()