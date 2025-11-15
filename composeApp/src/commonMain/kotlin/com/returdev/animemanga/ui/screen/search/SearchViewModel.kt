package com.returdev.animemanga.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.returdev.animemanga.ui.model.basic.VisualMediaBasicUi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

/**
 * An abstract ViewModel for a search screen that handles searching for visual media.
 *
 * @param T The type of the visual media items, which must extend [VisualMediaBasicUi].
 * @param dispatcher The CoroutineDispatcher to be used for background tasks.
 */
@OptIn(ExperimentalCoroutinesApi::class)
abstract class SearchViewModel<T : VisualMediaBasicUi>(
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchScreenState(items = flowOf(PagingData.empty<T>())))
    /**
     * The state of the search screen, exposed as a [StateFlow].
     */
    val uiState: StateFlow<SearchScreenState<T>> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(dispatcher) {
            _uiState.value = SearchScreenState(
                items = initialSearch()
            )
        }
    }

    /**
     * Performs an initial search when the ViewModel is created.
     *
     * @return A [Flow] of [PagingData] containing the initial search results.
     */
    abstract suspend fun initialSearch(): Flow<PagingData<T>>

    /**
     * Searches for visual media based on the given query.
     *
     * If the new query is the same as the last one, the search is skipped.
     *
     * @param query The search query.
     */
    fun search(query: String) {
        if (query == uiState.value.lastSearchQuery) {
            return
        }

        viewModelScope.launch(dispatcher) {
            _uiState.value = SearchScreenState(
                items = getSearchData(query),
                lastSearchQuery = query
            )
        }
    }

    /**
     * Retrieves the search data for the given query.
     *
     * @param query The search query.
     * @return A [Flow] of [PagingData] containing the search results.
     */
    abstract suspend fun getSearchData(query: String): Flow<PagingData<T>>
}
