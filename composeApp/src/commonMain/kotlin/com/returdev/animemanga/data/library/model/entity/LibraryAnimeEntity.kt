package com.returdev.animemanga.data.library.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate

/**
 * Represents an anime entry stored in the user's local library database.
 *
 * @property id The unique identifier of the anime (typically from the remote API).
 * @property image The URL or path to the anime's cover image.
 * @property title The title of the anime.
 * @property type The media type of the anime (e.g., "TV", "Movie", "OVA").
 * @property score The anime's average score or rating.
 * @property status The user’s library status for this anime (e.g., "watching", "completed").
 * @property addedDate The date the anime was added or last updated in the library.
 */
@Entity(
    tableName = "library_anime"
)
data class LibraryAnimeEntity(
    @PrimaryKey val id : Int,
    val image : String,
    val title : String,
    val type : String,
    val score : Float,
    val status : String,
    @ColumnInfo(name = "added_date") val addedDate : LocalDate
)
