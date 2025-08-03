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
     * @param request Lambda that performs the HTTP request and returns an HttpResponse.
     * @return ApiResponse\<T\> containing either the successful result or an error.
     * @throws Exception if all retries fail.
     */
    override suspend fun <T> executeRequest(
        request : suspend () -> HttpResponse,
        typeInfo : TypeInfo
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