package com.returdev.animemanga.ui.screen.home

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
    val topAnime : Flow<PagingData<AnimeBasicUi>> = flowOf(PagingData.empty()),
    val topManga : Flow<PagingData<MangaBasicUi>> = flowOf(PagingData.empty()),
    val animeCurrentSeason : Flow<PagingData<AnimeBasicUi>> = flowOf(PagingData.empty())
)
