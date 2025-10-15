package com.returdev.animemanga.data.library

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

fun getLibraryDatabase() : LibraryDatabase {
    val dbFilePath = getDatabasePath()

    return Room.databaseBuilder<LibraryDatabase>(dbFilePath)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()

}


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