package com.returdev.animemanga.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
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
    fun getMetadataEntityByName(name : String) : MetadataEntity?

    /**
     * Inserts a new metadata entity into the database.
     *
     * @param metadataEntity The [MetadataEntity] to be saved.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMetadataEntity(metadataEntity: MetadataEntity)

}