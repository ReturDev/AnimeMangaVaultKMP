package com.returdev.animemanga.data.remote.model.core.extension

import com.returdev.animemanga.data.remote.model.core.wrapper.data.DataResponse
import com.returdev.animemanga.data.remote.model.core.wrapper.data.PagedDataResponse
import com.returdev.animemanga.data.remote.model.core.wrapper.response.ApiHttpResponseStatus
import com.returdev.animemanga.data.remote.model.core.wrapper.response.ApiResponse
import com.returdev.animemanga.domain.model.core.result.DomainErrorType
import com.returdev.animemanga.domain.model.core.result.DomainResult


/**
 * Extension function that maps an [ApiResponse] containing paginated data ([PagedDataResponse])
 * into a [DomainResult] that the domain layer can consume.
 *
 * @param mapContent Function used to transform each element of type [T] into type [R].
 * @return A [DomainResult] that represents success with a paged list of items,
 *         or an error if the API call failed or the connection was lost.
 *
 * Cases:
 * - [ApiResponse.Success] → returns [DomainResult.PagedSuccess] with mapped list and pagination info.
 * - [ApiResponse.Failure] → converts the error status into a [DomainErrorType].
 * - [ApiResponse.ConnectionFailure] → maps to [DomainErrorType.NETWORK_ERROR].
 */
fun <T, R> ApiResponse<PagedDataResponse<T>>.toPagedDomainModel(
    mapContent : (T) -> R
) : DomainResult<List<R>> = when (this) {
    is ApiResponse.Success -> DomainResult.PagedSuccess(
        hasNextPage = content.pagination.hasNextPage,
        data = content.data.map(mapContent) // transforms each item into the domain model
    )

    is ApiResponse.Failure -> DomainResult.Error(errorStatus.toDomainErrorType())

    ApiResponse.ConnectionFailure -> DomainResult.Error(DomainErrorType.NETWORK_ERROR)
}


/**
 * Extension function that maps an [ApiResponse] containing a single [DataResponse]
 * into a [DomainResult] that the domain layer can consume.
 *
 * @param mapContent Function used to transform the data element of type [T] into type [R].
 * @return A [DomainResult] representing success with the mapped object,
 *         or an error if the API call failed or the connection was lost.
 *
 * Cases:
 * - [ApiResponse.Success] → returns [DomainResult.Success] with mapped single object.
 * - [ApiResponse.Failure] → converts the error status into a [DomainErrorType].
 * - [ApiResponse.ConnectionFailure] → maps to [DomainErrorType.NETWORK_ERROR].
 */

fun <T, R> ApiResponse<DataResponse<T>>.toDomainModel(
    mapContent : (T) -> R
) : DomainResult<R> = when (this) {
    is ApiResponse.Success -> {
        DomainResult.Success(
            data = mapContent(content.data) // transforms the single data object into domain model
        )
    }

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
