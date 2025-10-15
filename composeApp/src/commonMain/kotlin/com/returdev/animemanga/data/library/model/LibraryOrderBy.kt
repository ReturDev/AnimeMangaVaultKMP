package com.returdev.animemanga.data.library.model

/**
 * Defines the available ordering options for sorting items
 * in the user's anime or manga library.
 *
 * @property id Numeric identifier for the order type, used in SQL queries.
 */
enum class LibraryOrderBy(val id : Int) {


    ADDED_DATE(1),
    TITLE(2)

}
