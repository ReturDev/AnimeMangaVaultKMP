package com.returdev.animemanga.data.cache

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import kotlinx.coroutines.Dispatchers

/**
 * Creates and returns an instance of [CacheDataBase] for Android using Room.
 *
 * The database is built using the Android SQLite driver and is configured to use
 * the IO coroutine context for queries. The database file path is determined
 * by the provided [Context] and [DATABASE_NAME].
 *
 * @param context The Android [Context] used to access the database file path.
 * @return An instance of [CacheDataBase].
 */
fun getDatabase(context : Context) : CacheDataBase {
    val dbFile = context.getDatabasePath(DATABASE_NAME)
    return Room.databaseBuilder<CacheDataBase>(context, dbFile.absolutePath)
        .setDriver(AndroidSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}