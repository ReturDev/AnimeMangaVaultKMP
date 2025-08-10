package com.returdev.animemanga.data.cache

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

/**
 * Creates and returns an instance of [CacheDataBase] for iOS using Room.
 *
 * The database is built using a bundled SQLite driver and is configured to use
 * the IO coroutine context for queries. The database file path is determined
 * by [getDatabasePath].
 *
 * @return An instance of [CacheDataBase].
 */
fun getDatabase() : CacheDataBase {
    val dbFilePath = getDatabasePath()

    return Room.databaseBuilder<CacheDataBase>(name = dbFilePath)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()

}

/**
 * Retrieves the file path for the local cache database on iOS.
 *
 * Uses the default file manager to get the document directory URL and appends
 * the database name to construct the full path.
 *
 * @return The full file path to the database as a [String].
 */
@OptIn(ExperimentalForeignApi::class)
private fun getDatabasePath() : String {
    val fileDir : NSURL? = NSFileManager.defaultManager().URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null
    )

    return "${requireNotNull(fileDir).path!!}/${DATABASE_NAME}"

}