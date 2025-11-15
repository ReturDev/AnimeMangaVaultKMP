package com.returdev.animemanga.ui.screen.search

import androidx.paging.PagingData
import com.returdev.animemanga.ui.model.basic.VisualMediaBasicUi
import kotlinx.coroutines.flow.Flow

/**
 * Represents the state of the search screen.
 *
 * @param T The type of the visual media items.
 * @property lastSearchQuery The last search query entered by the user.
 * @property items A Flow of PagingData containing the search results.
 */
data class SearchScreenState<T : VisualMediaBasicUi>(
    val lastSearchQuery: String = "",
    val items: Flow<PagingData<T>>
)
