package com.returdev.animemanga.core.roompaging

import kotlinx.coroutines.flow.Flow


/**
 * A custom paging source implementation designed to fetch and manage paged data
 * from a Room database or any data source that supports offset-based pagination.
 *
 * @param T The type of items being paged.
 * @property config Configuration for paging, such as page size and maximum pages retained in memory.
 * @property getTotalItemCount A suspend function returning the total number of items available in the data source.
 * @property fetchData A suspend function used to retrieve a specific page of data based on a given `limit` and `offset`.
 */
class RoomPagingSource<T>(
    val config: RoomPagingConfig,
    private val getTotalItemCount: suspend () -> Int,
    private val fetchData: suspend (limit: Int, offset: Int) -> Flow<List<T>>
) {

    private var hasPrevPage = false
    private var hasNextPage = true

    companion object {
        private const val INITIAL_PAGE = 0
    }

    /** Tracks the index of the first page currently loaded in memory. */
    private var firstPage = INITIAL_PAGE

    /**
     * A mutable list that holds all currently loaded pages.
     *
     * Each entry in the list represents a page of data paired with its page number.
     */
    private val _loadedPages = mutableListOf<RoomPage<T>>()

    /**
     * A read-only list of all currently loaded pages.
     *
     * Exposes pages to external classes while preventing direct modification.
     */
    val loadedPages: List<RoomPage<T>> get() = _loadedPages.toList()

    /**
     * Loads a specific page of data from the data source.
     *
     * @param page The page number to load.
     * @return A [Flow] emitting the list of items for the requested page.
     */
    private suspend fun load(page: Int): Flow<List<T>> {
        val offset = page * config.pageSize
        val pageData = fetchData(config.pageSize, offset)

        hasPrevPage = firstPage > INITIAL_PAGE
        hasNextPage = offset + config.pageSize < getTotalItemCount()

        return pageData
    }

    /**
     * Loads the previous page (before the current first loaded page) if available.
     *
     * If a previous page exists, it replaces one of the cached pages (if the maximum
     * number of cached pages is exceeded) and prepends the new data.
     *
     * @return `true` if a previous page was successfully loaded, `false` otherwise.
     */
    suspend fun loadPreviousPage(): Boolean {
        if (!hasPrevPage) return false

        removePage(PageRemovalDirection.PREPEND)

        val page = loadedPages.firstOrNull()?.pageNumber?.minus(1) ?: INITIAL_PAGE
        val dataLoaded = load(page)

        _loadedPages.add(0, RoomPage(page, dataLoaded))

        return true
    }

    /**
     * Loads the next page (after the current last loaded page) if available.
     *
     * If a next page exists, it replaces one of the cached pages (if the maximum
     * number of cached pages is exceeded) and appends the new data.
     *
     * @return `true` if a next page was successfully loaded, `false` otherwise.
     */
    suspend fun loadNextPage(): Boolean {
        if (!hasNextPage) return false

        removePage(PageRemovalDirection.APPEND)

        val page = loadedPages.lastOrNull()?.pageNumber?.plus(1) ?: INITIAL_PAGE
        val dataLoaded = load(page)

        _loadedPages.add(RoomPage(page, dataLoaded))

        return true
    }

    /**
     * Removes a page from the loaded cache when the maximum number of retained pages is exceeded.
     *
     * Depending on the direction of navigation, it either removes the oldest (from start)
     * or newest (from end) page.
     *
     * @param direction Determines whether to remove from the start ([PageRemovalDirection.APPEND])
     * or from the end ([PageRemovalDirection.PREPEND]) of the cache.
     */
    private fun removePage(direction: PageRemovalDirection) {
        if (loadedPages.size < config.maxPages) return

        when (direction) {
            PageRemovalDirection.APPEND -> {
                _loadedPages.removeAt(0)
                firstPage++
            }
            PageRemovalDirection.PREPEND -> {
                _loadedPages.removeAt(_loadedPages.size - 1)
                firstPage--
            }
        }
    }

    /**
     * Defines the direction of page removal when trimming the loaded pages cache.
     */
    private enum class PageRemovalDirection { APPEND, PREPEND }
}
