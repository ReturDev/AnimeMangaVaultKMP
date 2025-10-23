package com.returdev.animemanga.domain.model.library

/**
 * Represents the different statuses a user can assign to an anime or manga
 * in their library at the domain/model layer.
 */
enum class UserLibraryStatusModel {
    FOLLOWING,
    FAVOURITES,
    COMPLETED,
    ON_HOLD,
    DROPPED
}
