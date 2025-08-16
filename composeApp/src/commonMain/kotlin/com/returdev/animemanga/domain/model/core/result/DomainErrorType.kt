package com.returdev.animemanga.domain.model.core.result

/**
 * Represents the different types of errors that can occur
 * when performing domain-level operations.
 */
enum class DomainErrorType {

    BAD_REQUEST_ERROR,
    INTERNAL_SERVER_ERROR,
    SERVICE_UNAVAILABLE_ERROR,
    NETWORK_ERROR,
    UNKNOWN_ERROR
}
