package com.returdev.animemanga.data.cache.datasource.genre

import com.returdev.animemanga.data.cache.model.entity.genre.GenreCacheEntity

abstract class GenreCacheDataSource<T : GenreCacheEntity> {

    suspend fun syncGenres(newGenres: List<T>): Boolean {
        val localGenres = getGenres()

        if (!hasSameGenres(localGenres, newGenres)) {
            replaceGenres(newGenres)
            return true
        }

        return false
    }

    abstract suspend fun getGenreById(id: Int): T?

    abstract suspend fun getGenres(): List<T>

    protected abstract suspend fun replaceGenres(newGenres: List<T>)

    protected fun hasSameGenres(
        localGenres: List<T>,
        newGenres: List<T>
    ): Boolean {
        if (localGenres.size != newGenres.size) return false
        return localGenres.sortedBy { it.id } == newGenres.sortedBy { it.id }
    }
}
