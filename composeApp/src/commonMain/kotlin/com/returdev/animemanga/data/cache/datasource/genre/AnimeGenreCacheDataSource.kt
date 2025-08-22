package com.returdev.animemanga.data.cache.datasource.genre

import com.returdev.animemanga.data.cache.dao.genre.AnimeGenreCacheDAO
import com.returdev.animemanga.data.cache.model.entity.genre.AnimeGenreCacheEntity


class AnimeGenreCacheDataSource(
    private val animeGenreCacheDAO : AnimeGenreCacheDAO
) : GenreCacheDataSource<AnimeGenreCacheEntity>() {
    override suspend fun getGenreById(id : Int) : AnimeGenreCacheEntity? = animeGenreCacheDAO.getGenreById(id)

    override suspend fun getGenres() : List<AnimeGenreCacheEntity> = animeGenreCacheDAO.getGenres()
    override suspend fun replaceGenres(
        newGenres : List<AnimeGenreCacheEntity>
    ) = animeGenreCacheDAO.replaceGenres(newGenres)
}