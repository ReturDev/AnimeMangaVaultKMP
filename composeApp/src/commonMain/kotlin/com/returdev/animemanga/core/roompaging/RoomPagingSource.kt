package com.returdev.animemanga.core.roompaging

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

/**
 * A lightweight custom paging mechanism for managing paginated data from a Room database (or any source).
 *
 * This class simulates paging behavior by loading chunks of data (pages) using a provided
 * data-fetching lambda. It keeps track of whether previous/next pages exist and removes
 * old pages when exceeding the maximum allowed.
 *
 * @param T The type of items being paged.
 * @param config Paging configuration such as page size and maximum number of cached pages.
 * @param maxItems The maximum number of items available (used to determine when to stop paging).
 * @param fetchData A suspend function that fetches a flow of data for a given page,
 *                  based on the limit (page size) and offset.
 */
class RoomPagingSource<T>(
    val config: RoomPagingConfig,
    private val maxItems: Int,
    private val fetchData: suspend (limit: Int, offset: Int) -> Flow<List<T>>
) {

    companion object {
        private const val INITIAL_PAGE = 1
    }

    private var hasPrevPage = false
    private var hasNextPage = true

    /**
     * Holds the currently loaded pages.
     *
     * Each entry in the list is a pair:
     * - Int → The page number.
     * - Flow<List<T>> → The flow of data for that page.
     */
    private val _pages = mutableListOf<Pair<Int, Flow<List<T>>>>()
    val pages: List<Pair<Int, Flow<List<T>>>> = _pages

    /**
     * Loads a specific page of data.
     *
     * @param page The page number to load.
     * @return A [Flow] of the list of items for the requested page.
     */
    private suspend fun load(page: Int): Flow<List<T>> {
        val offset = page * config.pageSize
        val pageData = fetchData(config.pageSize, offset)

        hasPrevPage = page != INITIAL_PAGE
        hasNextPage = (pageData.first().size + offset) < maxItems

        return pageData
    }

    /**
     * Loads and prepends a new page before the current list of pages.
     *
     * @param page The page number to prepend.
     */
    suspend fun prepend(page: Int) {
        if (!hasPrevPage) return

        removePage(RemovalDirection.PREPEND)

        _pages.add(0, Pair(page, load(page)))
    }

    /**
     * Loads and appends a new page after the current list of pages.
     *
     * @param page The page number to append.
     */
    suspend fun append(page: Int) {
        if (!hasNextPage) return

        removePage(RemovalDirection.APPEND)

        _pages.add(Pair(page, load(page)))
    }

    /**
     * Removes a page from the cached pages list when exceeding the allowed maximum.
     *
     * @param direction Determines whether to remove from the start (prepend)
     *                  or the end (append).
     */
    private fun removePage(direction: RemovalDirection) {
        if (pages.size < config.maxPages) return

        _pages.remove(
            when (direction) {
                RemovalDirection.APPEND -> _pages.first()  // Remove oldest when appending new
                RemovalDirection.PREPEND -> _pages.last()  // Remove latest when prepending new
            }
        )
    }

    /** Direction of removal when exceeding the maximum cached pages. */
    private enum class RemovalDirection { APPEND, PREPEND }
}
