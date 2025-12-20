package com.returdev.animemanga.data.paging

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadResult.*
import androidx.paging.PagingState
import com.returdev.animemanga.data.remote.model.core.wrapper.data.PagedDataResponse
import com.returdev.animemanga.data.remote.model.core.wrapper.response.ApiResponse
import com.returdev.animemanga.data.remote.service.ApiService
import com.returdev.animemanga.domain.model.core.result.DomainResult
import com.returdev.animemanga.domain.model.core.result.DomainResult.Error
import com.returdev.animemanga.domain.model.core.result.DomainResult.PagedSuccess
import com.returdev.animemanga.domain.model.core.result.DomainResult.Success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * A generic implementation of [PagingSource] that handles paginated data
 * fetching from a remote API and converts it into a domain-level representation.
 *
 * This class is flexible because it allows:
 * - Custom data fetching logic via [fetchPage]
 * - Transformation of API responses into domain results via [transform]
 *
 * @param T The raw data type returned from the API (e.g., DTO).
 * @param R The domain model type to be exposed to the UI.
 * @param dispatcher The [CoroutineDispatcher] on which network requests and transformations are executed.
 * @param itemsLimit Optional maximum number of items to load (useful to enforce client-side limits).
 * @param fetchPage A suspend function to fetch a page from the API by its number.
 * @param transform A function to convert [ApiResponse] into a [DomainResult].
 */
class GenericPagingSource<T, R : Any>(
    private val dispatcher : CoroutineDispatcher,
    private val itemsLimit : Int? = null,
    private val fetchPage : suspend (page : Int) -> ApiResponse<PagedDataResponse<T>>,
    private val transform : (ApiResponse<PagedDataResponse<T>>) -> DomainResult<List<R>>
) : PagingSource<Int, R>() {

    companion object {
        /** The starting page index for pagination (usually 1 for APIs). */
        private const val INITIAL_PAGE_INDEX = 1

        /** Default page size used in pagination. */
        const val PAGE_SIZE = ApiService.MAX_REQUEST_LIMIT

        /** Number of items ahead of the current viewport to load. */
        const val PREFETCH_DISTANCE = 5

        /**
         * Provides a default [PagingConfig] for creating a Pager.
         *
         * @return A [PagingConfig] configured with page size, prefetch distance, and max size.
         */
        fun getPagingConfig() : PagingConfig {
            return PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PREFETCH_DISTANCE,
                maxSize = PAGE_SIZE * 4
            )
        }
    }

    /**
     * Defines the key used to refresh the list when new data is requested.
     *
     * @param state The current [PagingState] containing loaded pages and anchor position.
     * @return The refresh key, which is usually the anchor position.
     */
    override fun getRefreshKey(state : PagingState<Int, R>) : Int? = state.anchorPosition

    /**
     * Loads a page of data from the API.
     *
     * @param params The [LoadParams] containing the page key and requested load size.
     * @return A [LoadResult] containing either a page of items or an error.
     */
    override suspend fun load(params : LoadParams<Int>) : LoadResult<Int, R> {
        val page = params.key ?: INITIAL_PAGE_INDEX

        val response = withContext(dispatcher) {
            transform(fetchPage(page))
        }

        return when (response) {
            is Error -> Error(Exception(response.errorType.name))
            is Success -> {
                throw IllegalArgumentException("Use PagedSuccess for paginated data")
            }
            is PagedSuccess -> {
                val totalSoFar = (page - 1) * params.loadSize + response.data.size
                val reachedLimit = itemsLimit?.let { limit ->
                    totalSoFar >= limit
                } ?: false

                val prevPage = if (page == INITIAL_PAGE_INDEX) null else page - 1
                val nextPage = if (response.hasNextPage && !reachedLimit) page + 1 else null

                val limitedData = if (reachedLimit) {
                    response.data.take(itemsLimit - (totalSoFar - response.data.size))
                } else {
                    response.data
                }

                Page(
                    data = limitedData,
                    prevKey = prevPage,
                    nextKey = nextPage
                )

            }
        }
    }
}
