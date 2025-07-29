package com.returdev.animemanga.data.remote.model.core.wrapper.response

/**
 * Sealed class representing the possible outcomes of an API call.
 *
 * @param T The type of the successful response content.
 */
sealed class ApiResponse<out T> {

    /**
     * Represents a successful API response containing the expected content.
     *
     * @param T The type of the content returned by the API.
     * @property content The actual data returned by the API.
     */
    data class Success<T>(val content : T) : ApiResponse<T>()

    /**
     * Represents a failed API response with a specific error status.
     *
     * @property errorStatus The error status describing the failure.
     */
    data class Failure(val errorStatus : ApiHttpResponseStatus.ErrorStatus) : ApiResponse<Nothing>()

    /**
     * Represents a failure due to connection issues (e.g., no internet).
     */
    data object ConnectionFailure : ApiResponse<Nothing>()

}