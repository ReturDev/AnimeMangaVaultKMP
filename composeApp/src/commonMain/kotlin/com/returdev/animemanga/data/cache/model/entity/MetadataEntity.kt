package com.returdev.animemanga.data.cache.model.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entity class representing a metadata record in the local database.
 *
 * @property id Auto-generated primary key for the metadata entry.
 * @property name Unique key or identifier for the metadata entry.
 * @property value Value associated with the metadata key.
 */
@Entity(
    tableName = "metadata_table",
    indices = [Index(value = ["name"], unique = true)]
)
data class MetadataEntity(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val name : String,
    val value : String
)