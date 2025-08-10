package com.returdev.animemanga.data.cache.datasource.genre

import com.returdev.animemanga.data.cache.dao.genre.MangaGenreCacheDAO
import com.returdev.animemanga.data.cache.model.entity.genre.MangaGenreCacheEntity

class MangaCacheDataSource(
    private val mangaGenreCacheDAO : MangaGenreCacheDAO
) : GenreCacheDataSource<MangaGenreCacheEntity>() {

    override suspend fun getGenreById(id : Int) : MangaGenreCacheEntity? =
        mangaGenreCacheDAO.getGenreById(id)

    override suspend fun getGenres() : List<MangaGenreCacheEntity> = mangaGenreCacheDAO.getGenres()

    override suspend fun replaceGenres(newGenres : List<MangaGenreCacheEntity>) =
        mangaGenreCacheDAO.replaceGenres(newGenres)
}