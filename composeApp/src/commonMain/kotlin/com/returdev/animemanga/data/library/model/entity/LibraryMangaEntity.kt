package com.returdev.animemanga.data.library.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate

/**
 * Represents a manga entry stored in the user's local library database.
 *
 * @property id Unique identifier for the manga (matches the remote API ID).
 * @property image URL of the manga’s cover or thumbnail image.
 * @property title Title of the manga.
 * @property type The format or category of the manga (e.g., "Manga", "Manhwa", "Light Novel").
 * @property score The user or community score associated with the manga.
 * @property status The user's current library status for the manga
 * (e.g., "READING", "COMPLETED", "PLAN_TO_READ").
 * @property addedDate The date when the manga was added to the user's library.
 */
@Entity(
    tableName = "library_manga"
)
data class LibraryMangaEntity(
    @PrimaryKey val id : Int,
    val image : String,
    val title : String,
    val type : String,
    val score : Float,
    val status : String,
    @ColumnInfo(name = "added_date") val addedDate : LocalDate
)
