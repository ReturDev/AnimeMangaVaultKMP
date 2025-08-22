package com.returdev.animemanga.data.paging

import androidx.paging.PagingSource
import com.returdev.animemanga.data.remote.model.core.wrapper.data.PagedDataResponse
import com.returdev.animemanga.data.remote.model.core.wrapper.response.ApiResponse
import com.returdev.animemanga.domain.model.core.result.DomainResult
import kotlinx.coroutines.CoroutineDispatcher

/**
 * A sealed base class for creating paging sources that load visual media
 * (e.g., anime, manga) from a remote API and transform them into domain models.
 *
 * This abstraction reduces boilerplate by delegating common paging setup to
 * [GenericPagingSource] while allowing subclasses to define their own
 * mapping from API responses to domain results.
 *
 * @param T The raw API model type (e.g., DTO).
 * @param R The domain model type (e.g., entity used by the UI).
 */
sealed class VisualMediaPagingSource<T, R : Any> {

    /** The [CoroutineDispatcher] on which API requests and transformations will be executed. */
    protected abstract val dispatcher : CoroutineDispatcher

    /**
     * Creates a [GenericPagingSource] for visual media.
     *
     * This function wires up a generic paging source with:
     * - [dispatcher] for executing requests
     * - [fetchPage] for retrieving paginated API data
     * - [itemsLimit] for enforcing a maximum number of items
     * - [mapResponseToDomain] for converting raw API responses to domain models
     *
     * @param itemsLimit Optional maximum number of items to load in total.
     * @param fetchPage A suspend function that fetches a page of results from the API.
     * @return A configured [GenericPagingSource] instance.
     */
    protected fun createVisualMediaPagingSource(
        itemsLimit : Int? = null,
        fetchPage : suspend (page : Int) -> ApiResponse<PagedDataResponse<T>>,
    ) : PagingSource<Int, R> {
        return GenericPagingSource(
            dispatcher = dispatcher,
            fetchPage = fetchPage,
            itemsLimit = itemsLimit,
            transform = ::mapResponseToDomain
        )
    }

    /**
     * Converts a raw [ApiResponse] of paginated data into a [DomainResult]
     * containing a list of domain models.
     *
     * Subclasses must implement this method to define how API data is
     * mapped into the domain layer.
     *
     * @param data The API response to transform.
     * @return A [DomainResult] containing a list of domain models.
     */
    protected abstract fun mapResponseToDomain(
        data : ApiResponse<PagedDataResponse<T>>
    ) : DomainResult<List<R>>
}
