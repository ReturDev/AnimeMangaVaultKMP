package com.returdev.animemanga.data.remote.model.core.extension

import com.returdev.animemanga.data.remote.model.core.wrapper.data.PagedDataResponse
import com.returdev.animemanga.data.remote.model.core.wrapper.response.ApiHttpResponseStatus
import com.returdev.animemanga.data.remote.model.core.wrapper.response.ApiResponse
import com.returdev.animemanga.domain.model.core.result.DomainErrorType
import com.returdev.animemanga.domain.model.core.result.DomainResult

/**
 * Converts a paged [ApiResponse] into a [DomainResult] containing a list of domain models.
 *
 * This function maps the content of a successful API response using [mapContent],
 * preserves pagination info for determining if a next page exists, and converts
 * failures into corresponding [DomainErrorType] values.
 *
 * @param T The raw API model type.
 * @param R The domain model type.
 * @param mapContent A lambda to convert individual items from [T] to [R].
 * @return A [DomainResult] containing a list of domain models or an error type.
 */
fun <T, R> ApiResponse<PagedDataResponse<T>>.toDomainModel(
    mapContent : (T) -> R
) : DomainResult<List<R>> = when (this) {
    is ApiResponse.Success -> DomainResult.Success(
        hasNextPage = content.pagination.hasNextPage,
        data = content.data.data.map(mapContent)
    )

    is ApiResponse.Failure -> DomainResult.Error(errorStatus.toDomainErrorType())

    ApiResponse.ConnectionFailure -> DomainResult.Error(DomainErrorType.NETWORK_ERROR)
}

/**
 * Converts a non-paged [ApiResponse] into a [DomainResult] of a single domain model.
 *
 * This is useful for API endpoints returning a single object instead of paginated data.
 *
 * @param T The raw API model type.
 * @param R The domain model type.
 * @param mapContent A lambda to convert the content from [T] to [R].
 * @return A [DomainResult] containing the domain model or an error type.
 */
fun <T, R> ApiResponse<T>.toDomainModel(
    mapContent : (T) -> R
) : DomainResult<R> = when (this) {
    is ApiResponse.Success -> DomainResult.Success(
        hasNextPage = false,
        data = mapContent(content)
    )

    is ApiResponse.Failure -> DomainResult.Error(errorStatus.toDomainErrorType())

    ApiResponse.ConnectionFailure -> DomainResult.Error(DomainErrorType.NETWORK_ERROR)
}

/**
 * Maps an API HTTP error status to a corresponding [DomainErrorType].
 *
 * This allows the domain layer to handle errors in a generic, consistent way
 * regardless of the raw HTTP response.
 *
 * @receiver The API error status from the response.
 * @return The corresponding [DomainErrorType].
 */
fun ApiHttpResponseStatus.ErrorStatus.toDomainErrorType() : DomainErrorType {
    return when (this) {
        ApiHttpResponseStatus.ErrorStatus.BadRequest -> DomainErrorType.BAD_REQUEST_ERROR
        ApiHttpResponseStatus.ErrorStatus.InternalServerError -> DomainErrorType.INTERNAL_SERVER_ERROR
        ApiHttpResponseStatus.ErrorStatus.ServiceDown -> DomainErrorType.SERVICE_UNAVAILABLE_ERROR
        else -> DomainErrorType.UNKNOWN_ERROR
    }
}
