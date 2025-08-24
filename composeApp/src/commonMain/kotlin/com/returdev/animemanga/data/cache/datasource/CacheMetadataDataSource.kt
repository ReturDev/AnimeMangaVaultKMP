package com.returdev.animemanga.data.cache.datasource

import com.returdev.animemanga.data.cache.dao.CacheMetadataDAO
import com.returdev.animemanga.data.cache.model.entity.MetadataEntity
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.monthsUntil
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

/**
 * Data source for managing metadata related to genres, seasons, and user information in the local cache.
 *
 * Provides methods to retrieve and update the last modified dates for genres and seasons metadata,
 * as well as saving and checking the user's birth date for age verification.
 *
 * @property metadataDAO The DAO used for metadata cache operations.
 */
@OptIn(ExperimentalTime::class)
class CacheMetadataDataSource(
    private val metadataDAO : CacheMetadataDAO
) {

    companion object {
        /** Metadata key for tracking last update of seasons metadata. */
        private const val UPDATED_SEASONS_METADATA_KEY = "updated_seasons_metadata"

        /** Metadata key for tracking last update of anime genres metadata. */
        private const val UPDATED_ANIME_GENRES_METADATA_KEY = "updated_anime_genres_metadata"

        /** Metadata key for tracking last update of manga genres metadata. */
        private const val UPDATED_MANGA_GENRES_METADATA_KEY = "updated_manga_genres_metadata"

        /** Metadata key for storing the user's birth date. */
        private const val USER_BIRTH_DATE_KEY = "user_birth_date"

        /** ISO date format used for storing and parsing metadata dates. */
        private val DATE_ISO_FORMAT = LocalDate.Formats.ISO
    }

    /** Cached value to store if the user is adult; null until computed. */
    private var isUserAdult : Boolean? = null

    /** Current system date in UTC. */
    private val currentDate : LocalDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date

    /**
     * Retrieves the last modified date for anime genres metadata.
     *
     * @return The [LocalDate] of the last update, or null if not available.
     */
    suspend fun getAnimeGenresLastModifiedDate() : LocalDate? =
        getLastModifiedDate(UPDATED_ANIME_GENRES_METADATA_KEY)

    /**
     * Retrieves the last modified date for manga genres metadata.
     *
     * @return The [LocalDate] of the last update, or null if not available.
     */
    suspend fun getMangaGenresLastModifiedDate() : LocalDate? =
        getLastModifiedDate(UPDATED_MANGA_GENRES_METADATA_KEY)

    /**
     * Retrieves the last modified date for seasons metadata.
     *
     * @return The [LocalDate] of the last update, or null if not available.
     */
    suspend fun getSeasonsLastModifiedDate() : LocalDate? =
        getLastModifiedDate(UPDATED_SEASONS_METADATA_KEY)

    /**
     * Updates the last modified date for anime genres metadata to the current date.
     */
    suspend fun updateAnimeGenresLastModifiedDate() =
        updateLastModifiedDate(UPDATED_ANIME_GENRES_METADATA_KEY)

    /**
     * Updates the last modified date for manga genres metadata to the current date.
     */
    suspend fun updateMangaGenresLastModifiedDate() =
        updateLastModifiedDate(UPDATED_MANGA_GENRES_METADATA_KEY)

    /**
     * Updates the last modified date for seasons metadata to the current date.
     */
    suspend fun updateSeasonsLastModifiedDate() =
        updateLastModifiedDate(UPDATED_SEASONS_METADATA_KEY)

    /**
     * Determines whether seasons metadata should be updated based on the last update date.
     *
     * @param monthsForUpdate Number of months after which an update is needed.
     * @return `true` if the metadata should be updated; `false` otherwise.
     */
    suspend fun shouldUpdateSeasons(monthsForUpdate : Int) : Boolean {
        return checkUpdateNeeded(
            lastModifiedDate = getSeasonsLastModifiedDate(),
            monthsForUpdate = monthsForUpdate
        )
    }

    /**
     * Determines whether anime genres metadata should be updated based on the last update date.
     *
     * @param monthsForUpdate Number of months after which an update is needed.
     * @return `true` if the metadata should be updated; `false` otherwise.
     */
    suspend fun shouldUpdateAnimeGenres(monthsForUpdate : Int) : Boolean {
        return checkUpdateNeeded(
            lastModifiedDate = getAnimeGenresLastModifiedDate(),
            monthsForUpdate = monthsForUpdate
        )
    }

    /**
     * Determines whether manga genres metadata should be updated based on the last update date.
     *
     * @param monthsForUpdate Number of months after which an update is needed.
     * @return `true` if the metadata should be updated; `false` otherwise.
     */
    suspend fun shouldUpdateMangaGenres(monthsForUpdate : Int) : Boolean {
        return checkUpdateNeeded(
            lastModifiedDate = getMangaGenresLastModifiedDate(),
            monthsForUpdate = monthsForUpdate
        )
    }

    /**
     * Saves the user's birth date in the metadata cache.
     *
     * @param birthDate The user's birth date to store.
     */
    suspend fun saveUserBirthDate(birthDate : LocalDate) {
        metadataDAO.saveMetadataEntity(
            MetadataEntity(
                name = USER_BIRTH_DATE_KEY,
                value = birthDate.toString()
            )
        )
    }

    /**
     * Checks if the user is considered an adult (18 years or older).
     *
     * Caches the result internally for future calls.
     *
     * @return `true` if the user is adult; `false` otherwise.
     */
    suspend fun isUserAdult() : Boolean {
        if (isUserAdult == null) {
            val userBirthDate = getLastModifiedDate(USER_BIRTH_DATE_KEY)!!
            val adultThresholdDate = currentDate.minus(18, DateTimeUnit.YEAR)
            isUserAdult = userBirthDate <= adultThresholdDate
        }
        return isUserAdult!!
    }

    /**
     * Retrieves the last modified date for a given metadata key.
     *
     * @param key The metadata key to look up.
     * @return The [LocalDate] of the last update, or null if not available.
     */
    private suspend fun getLastModifiedDate(key : String) : LocalDate? {
        val metadataValue = metadataDAO.getMetadataEntityByName(key)?.value
        return getLocalDateFromString(metadataValue)
    }

    /**
     * Converts a date string in ISO format to a [LocalDate].
     *
     * @param dateString The date string to parse.
     * @return The parsed [LocalDate], or null if the string is null.
     */
    private fun getLocalDateFromString(dateString : String?) : LocalDate? {
        return dateString?.let {
            LocalDate.parse(it, DATE_ISO_FORMAT)
        }
    }

    /**
     * Updates the last modified date for a given metadata key to the current date.
     *
     * @param key The metadata key to update.
     */
    private suspend fun updateLastModifiedDate(key : String) {
        metadataDAO.saveMetadataEntity(
            MetadataEntity(
                name = key,
                value = currentDate.toString()
            )
        )
    }

    /**
     * Determines whether an update is needed based on the last modified date.
     *
     * @param lastModifiedDate The last recorded update date.
     * @param monthsForUpdate The threshold in months for triggering an update.
     * @return `true` if an update is needed; `false` otherwise.
     */
    private fun checkUpdateNeeded(lastModifiedDate : LocalDate?, monthsForUpdate : Int) : Boolean {
        if (lastModifiedDate == null) return true
        val monthsDifference = lastModifiedDate.monthsUntil(currentDate)
        return monthsDifference >= monthsForUpdate
    }
}
