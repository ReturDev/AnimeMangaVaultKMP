package com.returdev.animemanga.ui.model.core

import kotlinx.datetime.LocalDate

/**
 * Represents a UI-friendly model for release date information.
 *
 * @property from The start date of the release period.
 * @property to The end date of the release period, or `null` if ongoing or unspecified.
 */
data class ReleasedUi(
    val from : LocalDate,
    val to : LocalDate?
) {

    /**
     * Converts the release period into a display-ready string.
     *
     * If an end date exists, the string follows the pattern:
     * `"MMM dd, yyyy to MMM dd, yyyy"`.
     *
     * If the end date is `null`, only the start date is shown.
     *
     * @return A formatted release date string.
     */
    override fun toString() : String {
        return buildString {
            append(formatDate(from))
            to?.let {
                append(" to ")
                append(formatDate(it))
            }
        }
    }

    /**
     * Formats a [LocalDate] into a short month-day-year string.
     *
     * Example output:
     * ```
     * Jan 12, 2020
     * ```
     *
     * @param date The date to format.
     * @return A compact display-friendly date string.
     */
    private fun formatDate(date : LocalDate) : String {
        val month = date.month.name.lowercase()
            .replaceFirstChar { it.uppercase() }
            .substring(0, 4)

        return "$month ${date.day}, ${date.year}"
    }
}
