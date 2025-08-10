package com.returdev.animemanga.data.cache.datasource

import androidx.room.Transactor
import androidx.room.useWriterConnection
import com.returdev.animemanga.data.cache.CacheDataBase
import com.returdev.animemanga.data.cache.dao.SeasonCacheDAO
import com.returdev.animemanga.data.cache.dao.YearCacheDAO
import com.returdev.animemanga.data.cache.model.entity.SeasonCacheEntity
import com.returdev.animemanga.data.cache.model.entity.YearCacheEntity

/**
 * Data source for managing season and year entities in the local cache.
 *
 * @property seasonCacheDAO DAO for season cache operations.
 * @property yearCacheDAO DAO for year cache operations.
 * @property dataBase The Room database instance for transaction management.
 */
class SeasonCacheDataSource(
    private val seasonCacheDAO: SeasonCacheDAO,
    private val yearCacheDAO: YearCacheDAO,
    private val dataBase : CacheDataBase
) {

    /**
     * Updates the local cache with a list of seasons.
     * If there are new seasons not yet cached, persists them in a transaction.
     *
     * @param seasons The list of [SeasonCacheEntity] to sync with the cache.
     * @return `true` if new seasons were persisted, `false` otherwise.
     */
    suspend fun updateSeasonsCache(seasons: List<SeasonCacheEntity>): Boolean {
        val lastCachedIndex = getLastCachedSeasonIndex(seasons)

        return when {
            lastCachedIndex == -1 -> {
                persistNewSeasons(seasons)
                true
            }
            lastCachedIndex >= 0 -> {
                persistNewSeasons(seasons.subList(0, lastCachedIndex))
                true
            }
            else -> false
        }
    }

    /**
     * Retrieves all years that have associated seasons in the cache.
     *
     * @return A list of years as [Int].
     */
    suspend fun getAllYearsWithSeasons(): List<Int> =
        yearCacheDAO.getAllYears().map(YearCacheEntity::year)

    /**
     * Retrieves the names of all seasons for a given year.
     *
     * @param year The year to query.
     * @return A list of season names as [String].
     */
    suspend fun getSeasonNamesByYear(year: Int): List<String> =
        seasonCacheDAO.getSeasonsByYear(year).map(SeasonCacheEntity::season)

    /**
     * Finds the index of the last cached season in the provided list.
     *
     * @param allSeasons The list of all [SeasonCacheEntity] to check.
     * @return The index of the last cached season, or -1 if none are cached.
     */
    private suspend fun getLastCachedSeasonIndex(allSeasons: List<SeasonCacheEntity>): Int {
        return seasonCacheDAO.getLastSeason()?.year?.let { lastCachedYear ->
            allSeasons.indexOfLast { it.year == lastCachedYear }
        } ?: -1
    }

    /**
     * Persists new seasons and their associated years into the cache within a transaction.
     *
     * @param seasons The list of [SeasonCacheEntity] to persist.
     */
    private suspend fun persistNewSeasons(seasons: List<SeasonCacheEntity>) {
        val seasonMap = seasons.groupBy { it.year }

        dataBase.useWriterConnection {
            it.withTransaction(Transactor.SQLiteTransactionType.IMMEDIATE){
                seasonMap.forEach { (year, seasonsOfYear) ->
                    yearCacheDAO.saveYear(YearCacheEntity(year))
                    seasonsOfYear.forEach { seasonCacheDAO.saveSeason(it) }
                }
            }
        }
    }
}