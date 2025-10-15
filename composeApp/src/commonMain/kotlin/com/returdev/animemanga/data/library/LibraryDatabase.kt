package com.returdev.animemanga.data.library

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.returdev.animemanga.data.library.dao.LibraryAnimeDAO
import com.returdev.animemanga.data.library.dao.LibraryMangaDAO
import com.returdev.animemanga.data.library.model.converter.LocalDateConverter
import com.returdev.animemanga.data.library.model.entity.LibraryAnimeEntity
import com.returdev.animemanga.data.library.model.entity.LibraryMangaEntity

const val DATABASE_NAME = "vault_library.db"

expect object LibraryCTor : RoomDatabaseConstructor<LibraryDatabase>

@Database(
    entities = [LibraryAnimeEntity::class, LibraryMangaEntity::class],
    version = 1
)
@ConstructedBy(LibraryCTor::class)
@TypeConverters(LocalDateConverter::class)
abstract class LibraryDatabase : RoomDatabase() {

    abstract fun libraryAnimeDao() : LibraryAnimeDAO
    abstract fun libraryMangaDao() : LibraryMangaDAO

}