package com.returdev.animemanga.domain.model.core.result

/**
 * Represents the result of a domain-level operation.
 *
 * @param T The type of data returned in case of success.
 */
sealed class DomainResult<out T> {

    /**
     * A successful operation result.
     *
     * @param hasNextPage Whether more data is available for pagination.
     * @param data The actual data returned from the operation.
     */
    data class Success<T>(
        val hasNextPage: Boolean,
        val data: T
    ) : DomainResult<T>()

    /**
     * An error result representing a failed operation.
     *
     * @param errorType The type of error that occurred (e.g., network, server, unknown).
     */
    data class Error(val errorType: DomainErrorType) : DomainResult<Nothing>()
}
