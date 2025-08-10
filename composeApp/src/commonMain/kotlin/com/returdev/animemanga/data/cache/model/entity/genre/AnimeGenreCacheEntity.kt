package com.returdev.animemanga.data.cache.model.entity.genre

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "anime_genres_table",
    indices = [Index(value = ["name"], unique = true)])
data class AnimeGenreCacheEntity(
    @PrimaryKey override val id : Int,
    override val name : String
) : GenreCacheEntity()
