package com.returdev.animemanga.domain.model.visualmedia

import com.returdev.animemanga.domain.model.core.ImageType

/**
 * Abstract class representing the basic information of a visual media item.
 *
 * @property id The unique identifier of the visual media.
 * @property images A list of images associated with the visual media.
 * @property title The title of the visual media.
 * @property type The type of the visual media (e.g., TV, Movie, OVA). Nullable.
 * @property score The score or rating of the visual media.
 */
abstract class VisualMediaBasicModel(){
    abstract val id : Int
    abstract val images : List<ImageType>
    abstract val title : String
    abstract val type : String?
    abstract val score : Float
}