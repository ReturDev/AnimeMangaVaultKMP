package com.returdev.animemanga.di

import androidx.room.RoomDatabase
import androidx.room.Transactor
import androidx.room.useReaderConnection
import com.returdev.animemanga.data.cache.CacheDataBase
import com.returdev.animemanga.data.cache.datasource.CacheMetadataDataSource
import com.returdev.animemanga.data.cache.datasource.SeasonCacheDataSource
import com.returdev.animemanga.data.cache.datasource.genre.AnimeGenreCacheDataSource
import com.returdev.animemanga.data.cache.datasource.genre.MangaGenreCacheDataSource
import com.returdev.animemanga.data.library.LibraryDatabase
import com.returdev.animemanga.data.library.datasource.LibraryAnimeDataSource
import com.returdev.animemanga.data.library.datasource.LibraryMangaDataSource
import com.returdev.animemanga.data.paging.AnimePagingSource
import com.returdev.animemanga.data.paging.MangaPagingSource
import com.returdev.animemanga.data.remote.core.util.ApiRequestManager
import com.returdev.animemanga.data.remote.core.util.ApiRequestManagerImpl
import com.returdev.animemanga.data.remote.repository.AnimeRemoteRepository
import com.returdev.animemanga.data.remote.repository.MangaRemoteRepository
import com.returdev.animemanga.data.remote.service.ApiService
import com.returdev.animemanga.data.repository.AnimeRepository
import com.returdev.animemanga.data.repository.LibraryRepository
import com.returdev.animemanga.data.repository.MangaRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val datamodule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(json = Json{ ignoreUnknownKeys = true }, contentType = ContentType.Any)
            }
            install(DefaultRequest) {
                url{
                    protocol = URLProtocol.HTTPS
                    host = "api.jikan.moe/v4"
                }
            }
        }
    }

    factory<ApiRequestManager>{ ApiRequestManagerImpl() }
    factory<CoroutineDispatcher>{ Dispatchers.IO }

    factoryOf(::ApiService)
    factoryOf(::AnimeRemoteRepository)
    factoryOf(::MangaRemoteRepository)

    factoryOf(::AnimeRepository)
    factoryOf(::MangaRepository)

    factoryOf(::AnimePagingSource)
    factoryOf(::MangaPagingSource)

    cacheDaoFactory()
    dataSourceFactory()

    single {
        val job = SupervisorJob()
        val dispatcher = get<CoroutineDispatcher>()
        CoroutineScope(dispatcher + job)
    }

    factory<LibraryAnimeDataSource> {
        LibraryAnimeDataSource(
            animeDAO = get(),
            transactionRunner = get<LibraryDatabase>()::transactionRunner
        )
    }

    factory<LibraryMangaDataSource> {
        LibraryMangaDataSource(
            mangaDAO = get(),
            transactionRunner = get<LibraryDatabase>()::transactionRunner
        )
    }


    factoryOf(::LibraryRepository)
    factory {
        val libraryDatabase = get<LibraryDatabase>()
        libraryDatabase.libraryAnimeDao()
    }
    factory {
        val libraryDatabase = get<LibraryDatabase>()
        libraryDatabase.libraryMangaDao()
    }


}

private fun Module.cacheDaoFactory(){
    factory{ get<CacheDataBase>().seasonsCacheDao()}
    factory { get<CacheDataBase>().cacheMetadataDao() }
    factory { get<CacheDataBase>().yearCacheDao() }
    factory { get<CacheDataBase>().animeGenreCacheDao() }
    factory { get<CacheDataBase>().mangaGenreCacheDao() }
}

private fun Module.dataSourceFactory(){
    factoryOf(::AnimeGenreCacheDataSource)
    factoryOf(::MangaGenreCacheDataSource)
    factoryOf(::SeasonCacheDataSource)
    factoryOf(::CacheMetadataDataSource)
}


private suspend fun RoomDatabase.transactionRunner(transaction : suspend () -> Unit) {
    this.useReaderConnection {
        it.withTransaction(Transactor.SQLiteTransactionType.IMMEDIATE) {
            return@withTransaction transaction()
        }
    }
}