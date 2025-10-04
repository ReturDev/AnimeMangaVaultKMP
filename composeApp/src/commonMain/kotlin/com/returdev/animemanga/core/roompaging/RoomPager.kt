package com.returdev.animemanga.core.roompaging

import androidx.compose.foundation.lazy.LazyListLayoutInfo
import androidx.compose.foundation.lazy.grid.LazyGridLayoutInfo
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * A pager implementation for Room-based data sources that supports
 * infinite scrolling, mapping, and reactive collection of paged data.
 *
 * @param T The type of items fetched from the RoomPagingSource.
 * @param R The type of items exposed after applying [mapper].
 * @property dispatcher The [CoroutineDispatcher] on which all paging operations are executed.
 * @property pagingSource The [RoomPagingSource] used to fetch pages from the database.
 * @property mapper A mapping function to convert items of type [T] to [R].
 */
class RoomPager<T, R>(
    private val dispatcher: CoroutineDispatcher,
    private val pagingSource: RoomPagingSource<T>,
    private val mapper: (T) -> R
) {

    /** Internal state flow representing the current pager state (Loading or Loaded). */
    private var _items = MutableStateFlow<RoomPagerState<R>>(RoomPagerState.Loading)

    /** Public read-only state flow of pager items. */
    private val items: StateFlow<RoomPagerState<R>> = _items

    /** Job controlling the pager's lifetime and child coroutines. */
    private val pagerControllerJob = SupervisorJob()

    /** Job for the current data collection coroutine. */
    private var currentJob: Job? = null

    /** CoroutineScope used for current page collection. */
    private var currentScope: CoroutineScope? = null

    /** Limit threshold to trigger a prepend (load previous pages) based on scroll position. */
    private val prependFetchLimit = with(pagingSource.config) { (maxPages * pageSize) / 3 }

    /** Limit threshold to trigger an append (load next pages) based on scroll position. */
    private val appendFetchLimit = with(pagingSource.config) {
        val total = maxPages * pageSize
        total - (total / 3)
    }

    /**
     * Initializes the pager for a vertical list.
     *
     * @param layoutInfo The [LazyListLayoutInfo] used to track scrolling.
     */
    suspend fun initialize(layoutInfo: LazyListLayoutInfo) {
        trackScroll(layoutInfo)

        repeat(pagingSource.config.maxPages) { page ->
            pagingSource.append(page)
        }

        collectPages(pages = pagingSource.pages)
    }

    /**
     * Initializes the pager for a grid layout.
     *
     * @param layoutInfo The [LazyGridLayoutInfo] used to track scrolling.
     */
    suspend fun initialize(layoutInfo: LazyGridLayoutInfo) {
        trackScroll(layoutInfo)

        repeat(pagingSource.config.maxPages) { page ->
            pagingSource.append(page)
        }

        collectPages(pages = pagingSource.pages)
    }

    /** Loads the next page from the [pagingSource] and updates the state. */
    private suspend fun append() {
        val lastPage = pagingSource.pages.lastOrNull()?.first
        lastPage?.let {
            pagingSource.append(it + 1)
            collectPages(pagingSource.pages)
        }
    }

    /** Loads the previous page from the [pagingSource] and updates the state. */
    private suspend fun prepend() {
        val firstPage = pagingSource.pages.firstOrNull()?.first
        firstPage?.let {
            pagingSource.prepend(it - 1)
            collectPages(pagingSource.pages)
        }
    }

    /**
     * Tracks scrolling for a grid layout to trigger prepend/append operations.
     *
     * @param layoutInfo The grid layout info to observe.
     */
    private fun trackScroll(layoutInfo: LazyGridLayoutInfo) {
        CoroutineScope(pagerControllerJob + dispatcher).launch {
            snapshotFlow { layoutInfo }.collect { layout ->
                val first = layout.visibleItemsInfo.firstOrNull()?.index ?: 0
                val last = layout.visibleItemsInfo.lastOrNull()?.index ?: 0
                handleScroll(first, last)
            }
        }
    }

    /**
     * Tracks scrolling for a vertical list to trigger prepend/append operations.
     *
     * @param layoutInfo The list layout info to observe.
     */
    private fun trackScroll(layoutInfo: LazyListLayoutInfo) {
        CoroutineScope(pagerControllerJob + dispatcher).launch {
            snapshotFlow { layoutInfo }.collect { layout ->
                val first = layout.visibleItemsInfo.firstOrNull()?.index ?: 0
                val last = layout.visibleItemsInfo.lastOrNull()?.index ?: 0
                handleScroll(first, last)
            }
        }
    }

    /**
     * Handles scroll events by determining whether to prepend or append pages.
     *
     * @param first The index of the first visible item.
     * @param last The index of the last visible item.
     */
    private suspend fun handleScroll(first: Int, last: Int) {
        if (first > 0 && first <= prependFetchLimit) {
            prepend()
        } else if (last >= appendFetchLimit) {
            append()
        }
    }

    /**
     * Collects the current pages from the paging source, maps them, and updates the state flow.
     *
     * @param pages A list of page number and corresponding flow pairs.
     */
    private fun collectPages(pages: List<Pair<Int, Flow<List<T>>>>) {
        if (pages.isEmpty()) return

        generateNewCoroutineScope()

        currentScope!!.launch {
            _items.update {
                RoomPagerState.Loaded(
                    combinePagesFlow(pages).first().map { mapper(it) }
                )
            }
        }
    }

    /** Cancels the previous coroutine job and generates a new one for page collection. */
    private fun generateNewCoroutineScope() {
        currentJob?.cancel()
        currentJob = SupervisorJob()
        currentScope = CoroutineScope(currentJob!! + dispatcher)
    }

    /**
     * Combines multiple page flows into a single flow of flattened items.
     *
     * @param pages The list of page flows to combine.
     * @return A flow containing all items across pages.
     */
    private fun combinePagesFlow(pages: List<Pair<Int, Flow<List<T>>>>): Flow<List<T>> {
        val pageFlows = pages.map { it.second }
        return combine(pageFlows) { lists ->
            lists.toList().flatten()
        }
    }

    /** Cancels all running coroutines and invalidates the pager. */
    fun invalidate() {
        pagerControllerJob.cancel()
        currentJob?.cancel()
    }
}
