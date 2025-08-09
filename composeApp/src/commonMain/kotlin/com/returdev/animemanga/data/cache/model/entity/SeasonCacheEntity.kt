package com.returdev.animemanga.data.cache.model.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Entity class representing a season record in the local database.
 *
 * @property id Unique identifier for the season record. Serves as the primary key and is auto-generated.
 * @property year The year associated with the season. References [YearCacheEntity].
 * @property season Name of the season (e.g., "Spring", "Summer").
 */
@Entity(
    tableName = "seasons_table",
    foreignKeys = [ForeignKey(
        entity = YearCacheEntity::class,
        parentColumns = ["year"],
        childColumns = ["year"],
        onDelete = ForeignKey.CASCADE,
    )]
)
data class SeasonCacheEntity(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val year : Int,
    val season : String
)