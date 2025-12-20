package com.returdev.animemanga.ui.screen.search.anime

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.returdev.animemanga.data.repository.AnimeRepository
import com.returdev.animemanga.domain.model.core.search.SearchFilters
import com.returdev.animemanga.domain.model.core.search.anime.AnimeTypeFilters
import com.returdev.animemanga.ui.model.basic.AnimeBasicUi
import com.returdev.animemanga.ui.model.extension.toUi
import com.returdev.animemanga.ui.screen.search.SearchViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * ViewModel for the Anime Search screen.
 *
 * This ViewModel is responsible for handling the business logic related to searching for anime.
 * It extends [SearchViewModel] with [AnimeBasicUi] as the type of item.
 *
 * @param dispatcher The coroutine dispatcher for background tasks.
 * @param animeRepository The repository to fetch anime data from.
 */
class AnimeSearchViewModel(
    dispatcher : CoroutineDispatcher,
    private val animeRepository : AnimeRepository
) : SearchViewModel<AnimeBasicUi>(dispatcher) {


    /**
     * Performs the initial search to populate the screen before any user query.
     *
     * It fetches a default list of TV anime.
     *
     * @return A [Flow] of [PagingData] containing [AnimeBasicUi] items.
     */
    override suspend fun initialSearch() : Flow<PagingData<AnimeBasicUi>> {
        return animeRepository.getAnimeSearch(
            query = "",
            filters = SearchFilters.AnimeFilters(
                AnimeTypeFilters.TV
            )
        ).map { paging -> paging.map { it.toUi() } }.cachedIn(viewModelScope)
    }


    /**
     * Fetches search data based on the provided query.
     *
     * @param query The search term entered by the user.
     * @return A [Flow] of [PagingData] containing the filtered [AnimeBasicUi] items.
     */
    override suspend fun getSearchData(query : String) : Flow<PagingData<AnimeBasicUi>> {
        return animeRepository.getAnimeSearch(
            query = query,
            filters = SearchFilters.AnimeFilters()
        ).map { paging -> paging.map { it.toUi() } }.cachedIn(viewModelScope)
    }


}