package com.returdev.animemanga.data.remote.core.util

import com.returdev.animemanga.data.remote.model.core.wrapper.response.ApiResponse
import io.ktor.client.statement.HttpResponse
import io.ktor.util.reflect.TypeInfo

/**
 * Interface for managing API requests and handling their responses.
 */
interface ApiRequestManager {

    /**
     * Executes a suspendable HTTP request and parses the response into an [ApiResponse] of type [T].
     *
     * @param typeInfo The [TypeInfo] describing the expected type of the response body.
     * @param request A suspend function that performs the HTTP request and returns an [HttpResponse].
     * @return An [ApiResponse] containing the parsed result of type [T].
     */
    suspend fun <T> executeRequest(
        typeInfo : TypeInfo,
        request : suspend () -> HttpResponse
    ) : ApiResponse<T>

}