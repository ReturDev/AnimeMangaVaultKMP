package com.returdev.animemanga.data.cache

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.returdev.animemanga.data.cache.dao.CacheMetadataDAO
import com.returdev.animemanga.data.cache.dao.SeasonCacheDAO
import com.returdev.animemanga.data.cache.dao.YearCacheDAO
import com.returdev.animemanga.data.cache.dao.genre.AnimeGenreCacheDAO
import com.returdev.animemanga.data.cache.dao.genre.MangaGenreCacheDAO
import com.returdev.animemanga.data.cache.model.entity.MetadataEntity
import com.returdev.animemanga.data.cache.model.entity.SeasonCacheEntity
import com.returdev.animemanga.data.cache.model.entity.YearCacheEntity
import com.returdev.animemanga.data.cache.model.entity.genre.AnimeGenreCacheEntity
import com.returdev.animemanga.data.cache.model.entity.genre.MangaGenreCacheEntity

/**
 * Constant representing the name of the local cache database file.
 */
const val DATABASE_NAME = "vault_cache.db"

/**
 * Platform-specific expected object for constructing the [CacheDataBase].
 * Should be implemented per platform to provide the correct RoomDatabaseConstructor.
 */
expect object CacheCTor : RoomDatabaseConstructor<CacheDataBase>

/**
 * Abstract Room database class for local caching.
 *
 * @property genreCacheDao Provides access to genre-related cache operations.
 * @property cacheMetadataDao Provides access to metadata cache operations.
 * @property seasonsCacheDao Provides access to season cache operations.
 * @property yearCacheDao Provides access to year cache operations.
 */
@Database(
    entities = [MetadataEntity::class, AnimeGenreCacheEntity::class, MangaGenreCacheEntity::class, SeasonCacheEntity::class, YearCacheEntity::class],
    version = 1
)
@ConstructedBy(CacheCTor::class)
abstract class CacheDataBase : RoomDatabase() {

    abstract fun animeGenreCacheDao(): AnimeGenreCacheDAO
    abstract fun mangaGenreCacheDao(): MangaGenreCacheDAO
    abstract fun cacheMetadataDao() : CacheMetadataDAO
    abstract fun seasonsCacheDao(): SeasonCacheDAO
    abstract fun yearCacheDao(): YearCacheDAO

}