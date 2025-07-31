package com.returdev.animemanga.domain.model.core

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.char

/**
 * Data class representing a release period with a start and optional end date.
 *
 * @property from The start date of the release period.
 * @property to The end date of the release period. Nullable if the end date is unknown or ongoing.
 */
data class ReleasedModel(
    val from : LocalDate,
    val to : LocalDate?
){

    /**
     * Returns a string representation of the release period in the format "dd/MM/yyyy - dd/MM/yyyy".
     * If the end date is null, it displays "??/??/??" as the end date.
     *
     * @return A formatted string representing the release period.
     */
    override fun toString(): String {
        val dateFormat = LocalDate.Format {
            day()
            char('/')
            monthNumber()
            char('/')
            year()
        }
        val fromFormatted = from.format(dateFormat)
        val toFormatted = to?.format(dateFormat) ?: "??/??/??"

        return "$fromFormatted - $toFormatted"
    }
}