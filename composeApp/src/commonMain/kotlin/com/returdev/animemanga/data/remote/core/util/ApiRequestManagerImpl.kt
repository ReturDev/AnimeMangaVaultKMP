package com.returdev.animemanga.data.remote.core.util

import com.returdev.animemanga.data.remote.model.core.wrapper.response.ApiHttpResponseStatus
import com.returdev.animemanga.data.remote.model.core.wrapper.response.ApiResponse
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.util.reflect.TypeInfo
import kotlinx.coroutines.delay
import kotlinx.io.IOException

/**
 * Implementation of an API request manager that handles retries and exponential backoff for HTTP requests.
 */
class ApiRequestManagerImpl(
    private val delayFunction : suspend (Long) -> Unit = {delay(it)}
) : ApiRequestManager {

    companion object {
        const val MAX_RETRIES = 5
        const val INITIAL_DELAY_MILLIS = 1000L
        const val MAXIMUM_DELAY_MILLIS = 3000L
        const val DELAY_MULTIPLIER = 1.5f
    }


/**
     * Executes an HTTP request with retry logic and exponential backoff.
     *
     * @param typeInfo The type information used to deserialize the response body.
     * @param request A suspending lambda that performs the HTTP request and returns an [HttpResponse].
     * @return [ApiResponse] containing the result of the request, which can be a success, failure, or connection failure.
     *
     * The function will retry the request up to [MAX_RETRIES] times if a "Too Many Requests" error is received,
     * applying exponential backoff between retries. If a successful response is received, it returns a success response.
     * For other error statuses, it returns a failure response. If an [IOException] occurs, it returns a connection failure.
     */
    override suspend fun <T> executeRequest(
        typeInfo : TypeInfo,
        request : suspend () -> HttpResponse
    ) : ApiResponse<T> {

        var retries = 1
        var currentDelay = INITIAL_DELAY_MILLIS

        var result : ApiResponse<T>? = null

        try {
            while (retries <= MAX_RETRIES) {

                val response = request()
                val responseStatus = ApiHttpResponseStatus.fromCode(response.status.value)

                when (responseStatus) {
                    ApiHttpResponseStatus.ErrorStatus.TooManyRequest -> {
                        delayFunction(currentDelay)
                        val newDelay = (currentDelay * DELAY_MULTIPLIER).toLong()
                        currentDelay = newDelay.coerceAtMost(MAXIMUM_DELAY_MILLIS)
                        retries++
                    }

                    ApiHttpResponseStatus.SuccessStatus -> {
                        result = ApiResponse.Success(response.body(typeInfo))
                        break
                    }

                    else -> {
                        result = ApiResponse.Failure((responseStatus as ApiHttpResponseStatus.ErrorStatus))
                        break
                    }

                }
            }

        }catch (_ : IOException) {
            result = ApiResponse.ConnectionFailure
        }

        return result!!

    }
}