package com.returdev.animemanga.ui.screen.search.manga

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.returdev.animemanga.data.repository.MangaRepository
import com.returdev.animemanga.domain.model.core.search.SearchFilters
import com.returdev.animemanga.domain.model.core.search.manga.MangaTypeFilters
import com.returdev.animemanga.ui.model.basic.MangaBasicUi
import com.returdev.animemanga.ui.model.extension.toUi
import com.returdev.animemanga.ui.screen.search.SearchViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * ViewModel for the Manga Search screen.
 *
 * This ViewModel is responsible for handling the business logic related to searching for manga.
 * It extends [SearchViewModel] with [MangaBasicUi] as the type of item.
 *
 * @param dispatcher The coroutine dispatcher for background tasks.
 * @param mangaRepository The repository to fetch manga data from.
 */
class MangaSearchViewModel(
    dispatcher : CoroutineDispatcher,
    private val mangaRepository : MangaRepository
) : SearchViewModel<MangaBasicUi>(dispatcher) {

    /**
     * Performs the initial search to populate the screen before any user query.
     *
     * It fetches a default list of manga.
     *
     * @return A [Flow] of [PagingData] containing [MangaBasicUi] items.
     */
    override suspend fun initialSearch() : Flow<PagingData<MangaBasicUi>> {
        return mangaRepository.getMangaSearch(
            query = "",
            filters = SearchFilters.MangaFilters(
                type = MangaTypeFilters.MANGA
            )
        ).map { pagingData -> pagingData.map { it.toUi() } }.cachedIn(viewModelScope)
    }

    /**
     * Fetches search data based on the provided query.
     *
     * @param query The search term entered by the user.
     * @return A [Flow] of [PagingData] containing the filtered [MangaBasicUi] items.
     */
    override suspend fun getSearchData(query : String) : Flow<PagingData<MangaBasicUi>> {
        return mangaRepository.getMangaSearch(
            query = query,
            filters = SearchFilters.MangaFilters()
        ).map { pagingData -> pagingData.map { it.toUi() } }.cachedIn(viewModelScope)
    }
}