package com.returdev.animemanga.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.returdev.animemanga.data.cache.model.entity.MetadataEntity

/**
 * Data Access Object (DAO) for managing metadata entities in the local cache database.
 */
@Dao
interface CacheMetadataDAO {

    /**
     * Retrieves a metadata entity by its name.
     *
     * @param name The unique key or identifier of the metadata entry.
     * @return [MetadataEntity] corresponding to the given name.
     */
    @Query("SELECT * FROM metadata_table WHERE name = :name")
    fun getMetadataEntityByName(name : String) : MetadataEntity

    /**
     * Inserts a new metadata entity into the database.
     *
     * @param metadataEntity The [MetadataEntity] to be saved.
     */
    @Insert
    fun saveMetadataEntity(metadataEntity: MetadataEntity)

    /**
     * Updates the value of an existing metadata entity identified by its name.
     *
     * @param name The unique key or identifier of the metadata entry.
     * @param value The new value to set for the metadata entry.
     */
    @Query("UPDATE metadata_table SET value = :value WHERE name = :name")
    fun updateMetadataEntity(name: String, value: String)

}