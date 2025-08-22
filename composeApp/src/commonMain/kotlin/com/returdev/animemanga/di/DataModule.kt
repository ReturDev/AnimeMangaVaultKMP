package com.returdev.animemanga.di

import com.returdev.animemanga.data.cache.datasource.CacheMetadataDataSource
import com.returdev.animemanga.data.cache.datasource.SeasonCacheDataSource
import com.returdev.animemanga.data.cache.datasource.genre.AnimeGenreCacheDataSource
import com.returdev.animemanga.data.cache.datasource.genre.MangaGenreCacheDataSource
import com.returdev.animemanga.data.remote.core.util.ApiRequestManager
import com.returdev.animemanga.data.remote.core.util.ApiRequestManagerImpl
import com.returdev.animemanga.data.remote.service.ApiService
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
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
    factoryOf(::ApiService)
    factory<ApiRequestManager>{ ApiRequestManagerImpl() }
    factoryOf(::AnimeGenreCacheDataSource)
    factoryOf(::MangaGenreCacheDataSource)
    factoryOf(::CacheMetadataDataSource)
    factoryOf(::SeasonCacheDataSource)
}