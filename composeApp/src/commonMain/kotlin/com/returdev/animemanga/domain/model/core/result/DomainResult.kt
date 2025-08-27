package com.returdev.animemanga.domain.model.core.result

/**
 * Represents the result of a domain-level operation.
 *
 * @param T The type of data returned in case of success.
 */
sealed class DomainResult<out T> {

    /**
     * A successful result containing non-paginated data.
     *
     * @param T The type of the result data.
     * @property data The successfully retrieved domain data.
     */
    data class Success<T>(
        val data: T
    ) : DomainResult<T>()

    /**
     * A successful result specifically for paginated responses.
     *
     * Used when fetching data that supports paging (e.g., lists of anime/manga).
     *
     * @param T The type of the result data (usually a collection).
     * @property hasNextPage Indicates whether more pages of data are available.
     * @property data The retrieved domain data for the current page.
     */
    data class PagedSuccess<T>(
        val hasNextPage: Boolean,
        val data: T
    ) : DomainResult<T>()

    /**
     * An error result representing a failed operation.
     *
     * Used when the repository or use case fails to fetch/process data.
     *
     * @property errorType The type of error that occurred (e.g., network, server, unknown).
     */
    data class Error(val errorType: DomainErrorType) : DomainResult<Nothing>()
}
