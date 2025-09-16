package com.returdev.animemanga.ui.screen.home

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.returdev.animemanga.ui.model.basic.AnimeBasicUi
import com.returdev.animemanga.ui.model.basic.MangaBasicUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Represents the UI state for the Home screen.
 *
 * Each property contains a [Flow] of [PagingData], which provides
 * paginated lists of anime or manga items to be displayed in the UI.
 *
 * @property topAnime A paginated flow of the top-ranked anime list.
 * @property topManga A paginated flow of the top-ranked manga list.
 * @property animeCurrentSeason A paginated flow of anime from the current season.
 */
data class HomeScreenState(
    val topAnime : Flow<PagingData<AnimeBasicUi>> = initialPagingDataFlow(),
    val topManga : Flow<PagingData<MangaBasicUi>> = initialPagingDataFlow(),
    val animeCurrentSeason : Flow<PagingData<AnimeBasicUi>> = initialPagingDataFlow()
)

/**
 * Provides an initial [Flow] of [PagingData] in a "loading" state.
 *
 * This is used to initialize the state before real data is loaded,
 * allowing the UI to immediately react by showing shimmer placeholders
 * or loading indicators.
 *
 * @param T The type of data the paging flow will hold.
 * @return A [Flow] emitting a single [PagingData] instance with:
 *   - [LoadState.Loading] for `refresh` (indicating initial load is in progress).
 *   - [LoadState.NotLoading] for `append` and `prepend` (no additional pages yet).
 */
private fun <T : Any> initialPagingDataFlow() = flowOf(
    PagingData.from(
        emptyList<T>(), // No items initially
        LoadStates(
            refresh = LoadState.Loading,
            append = LoadState.NotLoading(false),
            prepend = LoadState.NotLoading(false)
        )
    )
)
