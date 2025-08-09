package com.returdev.animemanga.data.cache.model.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


/**
 * Entity class representing a genre record in the local database.
 *
 * @property id Unique identifier for the genre. Serves as the primary key.
 * @property name Name of the genre.
 */
@Entity(
    tableName = "genres_table",
    indices = [Index(value = ["name"], unique = true)])
data class GenreCacheEntity (
    @PrimaryKey val id : Int,
    val name : String
)