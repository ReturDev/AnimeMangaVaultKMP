package com.returdev.animemanga.domain.model.core

/**
 * Represents the four seasons of the year.
 */
enum class Season {

    WINTER,
    SPRING,
    SUMMER,
    FALL;

    companion object {
        /**
         * Converts a string to a corresponding [Season] enum value.
         *
         * @param value The string representation of the season.
         * @return The matching [Season] enum value, or `null` if no match is found.
         */
        fun fromString(value: String): Season? {
            return entries.find { it.name.equals(value, ignoreCase = true) }
        }
    }
}
