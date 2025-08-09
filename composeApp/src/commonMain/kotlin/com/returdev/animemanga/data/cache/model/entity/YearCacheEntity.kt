package com.returdev.animemanga.data.cache.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity class representing a season year record in the local database.
 *
 * @property year Unique identifier for the season year. Serves as the primary key.
 */
@Entity(tableName = "season_year_table")
data class YearCacheEntity(
    @PrimaryKey val year: Int
)