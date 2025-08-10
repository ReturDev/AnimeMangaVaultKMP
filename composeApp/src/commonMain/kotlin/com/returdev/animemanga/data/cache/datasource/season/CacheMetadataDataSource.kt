package com.returdev.animemanga.data.cache.datasource.season

import com.returdev.animemanga.data.cache.dao.CacheMetadataDAO
import com.returdev.animemanga.data.cache.model.entity.MetadataEntity
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

/**
 * Data source for managing metadata related to genres and seasons in the local cache.
 *
 * Provides methods to retrieve and update the last modified dates for genres and seasons metadata.
 *
 * @property metadataDAO The DAO used for metadata cache operations.
 */
@OptIn(ExperimentalTime::class)
class CacheMetadataDataSource(
    private val metadataDAO : CacheMetadataDAO
) {

    companion object {

        private const val UPDATED_SEASONS_METADATA_KEY = "updated_seasons_metadata"

        private const val UPDATED_GENRES_METADATA_KEY = "updated_genres_metadata"

        private val DATE_ISO_FORMAT = LocalDate.Formats.ISO
    }

    /**
     * Retrieves the last modified date for genres metadata.
     *
     * @return The [LocalDate] of the last update, or null if not available.
     */
    suspend fun getGenresLastModifiedDate() : LocalDate? = getLastModifiedDate(UPDATED_GENRES_METADATA_KEY)

    /**
     * Retrieves the last modified date for seasons metadata.
     *
     * @return The [LocalDate] of the last update, or null if not available.
     */
    suspend fun getSeasonsLastModifiedDate() : LocalDate? = getLastModifiedDate(UPDATED_SEASONS_METADATA_KEY)

    /**
     * Updates the last modified date for genres metadata to the current date and time.
     */
    suspend fun updateGenresLastModifiedDate() = updateLastModifiedDate(UPDATED_GENRES_METADATA_KEY)

    /**
     * Updates the last modified date for seasons metadata to the current date and time.
     */
    suspend fun updateSeasonsLastModifiedDate() = updateLastModifiedDate(UPDATED_SEASONS_METADATA_KEY)

    /**
     * Retrieves the last modified date for a given metadata key.
     *
     * @param key The metadata key to look up.
     * @return The [LocalDate] of the last update, or null if not available.
     */
    private suspend fun getLastModifiedDate(key : String): LocalDate? {
        val metadataValue = metadataDAO.getMetadataEntityByName(key)?.value
        return getLocalDateFromString(metadataValue)
    }

    /**
     * Converts a date string in ISO format to a [LocalDate].
     *
     * @param dateString The date string to parse.
     * @return The parsed [LocalDate], or null if the string is null.
     */
    private suspend fun getLocalDateFromString(dateString: String?): LocalDate? {
        return dateString?.let {
            LocalDate.parse(it, DATE_ISO_FORMAT)
        }
    }

    /**
     * Updates the last modified date for a given metadata key to the current date and time.
     *
     * @param key The metadata key to update.
     */
    private suspend fun updateLastModifiedDate(
        key: String
    ) {

        val currentDate = Clock.System.now().toLocalDateTime(TimeZone.UTC)

        metadataDAO.saveMetadataEntity(
            MetadataEntity(
                name = key,
                value = currentDate.toString()
            )
        )
    }

}