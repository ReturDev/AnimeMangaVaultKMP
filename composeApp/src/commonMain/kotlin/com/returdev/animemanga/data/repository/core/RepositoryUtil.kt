package com.returdev.animemanga.data.repository.core

import com.returdev.animemanga.data.remote.model.core.extension.toDomainErrorType
import com.returdev.animemanga.data.remote.model.core.wrapper.response.ApiResponse
import com.returdev.animemanga.domain.model.core.result.DomainErrorType
import com.returdev.animemanga.domain.model.core.result.DomainResult

/**
 * Utility object for repository-related operations.
 *
 * Provides helper methods for handling API responses and updating local cache safely.
 */
object RepositoryUtil {

    /**
     * Updates the cache with new data if the given condition is met.
     *
     * This function executes the following steps:
     * - If [shouldUpdate] is `true` and the [apiResponse] is a [ApiResponse.Success],
     *   it invokes [onSuccess] with the response content to update the cache.
     * - If [shouldUpdate] is `true` but the [apiResponse] is an error, it maps the error
     *   to a [DomainResult.Error] with the proper [DomainErrorType].
     * - If [shouldUpdate] is `false`, no action is taken and a successful [DomainResult] with `Unit` is returned.
     *
     * @param shouldUpdate Flag indicating whether cache update should be performed.
     * @param apiResponse The API response containing either success data or an error.
     * @param onSuccess A suspending lambda that will be executed if the response is successful,
     *                  typically used to persist data into the cache/database.
     *
     * @return A [DomainResult] representing either success (with `Unit` data) or an error.
     */
    suspend fun <T> updateCacheWithNewData(
        shouldUpdate: Boolean,
        apiResponse: ApiResponse<T>,
        onSuccess: suspend (T) -> Unit,
    ): DomainResult<Unit> {

        var result: DomainResult<Unit> = DomainResult.Success(
            hasNextPage = false,
            data = Unit
        )

        if (shouldUpdate) {
            when (apiResponse) {
                is ApiResponse.Success<T> -> {
                    onSuccess(apiResponse.content)
                }
                ApiResponse.ConnectionFailure -> {
                    result = DomainResult.Error(DomainErrorType.NETWORK_ERROR)
                }
                is ApiResponse.Failure -> {
                    result = DomainResult.Error(apiResponse.errorStatus.toDomainErrorType())
                }
            }
        }

        return result
    }
}
