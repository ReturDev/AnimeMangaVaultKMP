package com.returdev.animemanga.core.roompaging

import kotlinx.coroutines.flow.Flow

/**
 * Represents a single page of data used within the Room-based paging system.
 *
 * @param T The type of items contained within this page.
 * @property pageNumber The index of the current page .
 * @property data A [Flow] emitting the list of items belonging to this page.
 */
data class RoomPage<T>(
    val pageNumber: Int,
    val data : Flow<List<T>>
)
