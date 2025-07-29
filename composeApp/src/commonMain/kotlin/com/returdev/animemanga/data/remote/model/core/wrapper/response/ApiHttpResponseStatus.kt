package com.returdev.animemanga.data.remote.model.core.wrapper.response

import com.returdev.animemanga.data.remote.model.core.wrapper.response.ApiHttpResponseStatus.ErrorStatus.BadRequest
import com.returdev.animemanga.data.remote.model.core.wrapper.response.ApiHttpResponseStatus.ErrorStatus.InternalServerError
import com.returdev.animemanga.data.remote.model.core.wrapper.response.ApiHttpResponseStatus.ErrorStatus.ServiceDown
import com.returdev.animemanga.data.remote.model.core.wrapper.response.ApiHttpResponseStatus.ErrorStatus.TooManyRequest
import com.returdev.animemanga.data.remote.model.core.wrapper.response.ApiHttpResponseStatus.ErrorStatus.UNKNOWN

/**
 * Sealed class representing the possible HTTP response statuses for API calls.
 *
 * @property code The HTTP status code associated with the response status.
 */
sealed class ApiHttpResponseStatus(val code : Int) {

    /**
     * Represents a successful HTTP response (status code 200).
     */
    data object SuccessStatus : ApiHttpResponseStatus(200)

    /**
     * Sealed class representing error HTTP response statuses.
     *
     * @property code The HTTP status code for the error.
     */
    sealed class ErrorStatus(code : Int) : ApiHttpResponseStatus(code) {

        /**
         * Represents a Bad Request error (status code 400).
         */
        data object BadRequest : ErrorStatus(400)

        /**
         * Represents a Too Many Requests error (status code 429).
         */
        data object TooManyRequest : ErrorStatus(429)

        /**
         * Represents an Internal Server Error (status code 500).
         */
        data object InternalServerError : ErrorStatus(500)

        /**
         * Represents a Service Down error (status code 503).
         */
        data object ServiceDown : ErrorStatus(503)

        /**
         * Represents an unknown error (status code -1).
         */
        data object UNKNOWN : ErrorStatus(-1)

    }

    companion object {

        /**
         * Returns the corresponding [ApiHttpResponseStatus] for a given HTTP status code.
         *
         * @param code The HTTP status code to map.
         * @return The matching [ApiHttpResponseStatus] or [ErrorStatus.UNKNOWN] if not recognized.
         */
        fun fromCode(code : Int) : ApiHttpResponseStatus {
            return when(code) {
                SuccessStatus.code -> SuccessStatus
                BadRequest.code -> BadRequest
                TooManyRequest.code -> TooManyRequest
                InternalServerError.code -> InternalServerError
                ServiceDown.code -> ServiceDown
                else -> UNKNOWN
            }
        }
    }

}