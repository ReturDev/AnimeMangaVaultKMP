package com.returdev.animemanga.domain.model.core

/**
 * Data class representing a genre with its unique identifier and name.
 *
 * @property id Unique identifier for the genre.
 * @property name Name of the genre.
 */
data class GenreModel(
    val id : Int,
    val name : String,
)