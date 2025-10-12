package com.returdev.animemanga.core.roompaging

import androidx.compose.foundation.lazy.grid.LazyGridLayoutInfo
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
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
    private val dispatcher : CoroutineDispatcher,
    private val pagingSource : RoomPagingSource<T>,
    private val mapper : (T) -> R
) {

    /** Internal state flow representing the current pager state (Loading or Loaded). */
    private var _items = MutableStateFlow<RoomPagerState<R>>(RoomPagerState.Loading)

    /** Public read-only state flow of pager items. */
    val items : StateFlow<RoomPagerState<R>> = _items

    /** Job controlling the pager's lifetime and child coroutines. */
    private val pagerControllerJob = SupervisorJob()

    /** Job for the current data collection coroutine. */
    private var currentJob : Job? = null

    /** CoroutineScope used for current page collection. */
    private var currentScope : CoroutineScope? = null

    /** Limit threshold to trigger a prepend (load previous pages) based on scroll position. */
    private val prependFetchLimit = with(pagingSource.config) { (maxPages * pageSize) / 3 }

    /** Limit threshold to trigger an append (load next pages) based on scroll position. */
    private val appendFetchLimit = with(pagingSource.config) {
        val total = maxPages * pageSize
        total - (total / 3)
    }

    /**
     * Initializes the pager for a grid layout.
     *
     * @param layoutInfo The [LazyGridLayoutInfo] used to track scrolling.
     */
    suspend fun initialize(layoutInfo : LazyGridState) {
        trackScroll(layoutInfo)

        repeat(pagingSource.config.maxPages) {
            pagingSource.loadNextPage()
        }

        collectPages(pages = pagingSource.loadedPages)
    }

    /** Loads the next page from the [pagingSource] and updates the state. */
    private suspend fun append() {
        pagingSource.loadNextPage().also {
            if (it) collectPages(pagingSource.loadedPages)
        }
    }

    /** Loads the previous page from the [pagingSource] and updates the state. */
    private suspend fun prepend() {
        pagingSource.loadPreviousPage().also {
            if (it) collectPages(pagingSource.loadedPages)
        }
    }



    private fun trackScroll(listState : LazyGridState) {
        CoroutineScope(pagerControllerJob + dispatcher).launch {
            snapshotFlow { listState.firstVisibleItemIndex}
                .distinctUntilChanged()
                .collect { first ->
                    handleScroll(first)
                }
        }
    }


    /**
     * Handles scroll events by determining whether to prepend or append pages.
     *
     * @param first The index of the first visible item.
     * @param last The index of the last visible item.
     */
    private suspend fun handleScroll(first : Int) {
        if (first >= appendFetchLimit) {
            append()
        } else if (first > 0 && first < prependFetchLimit) {
            prepend()
        }

    }

    private fun collectPages(pages : List<RoomPage<T>>) {
        if (pages.isEmpty()) return

        generateNewCoroutineScope()

        currentScope!!.launch {
            combinePagesFlow(pages).collect { combinedList ->
                _items.value = RoomPagerState.Loaded(
                    combinedList.map { mapper(it) }
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
    private fun combinePagesFlow(pages : List<RoomPage<T>>) : Flow<List<T>> {
        val pageFlows = pages.map { it.data }
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
