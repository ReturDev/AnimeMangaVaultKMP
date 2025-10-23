package com.returdev.animemanga.data.library.model.converter

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate

/**
 * Provides type converters for [LocalDate] to enable Room database to store
 * and retrieve [LocalDate] values as Strings.
 */
class LocalDateConverter {

    /**
     * Converts a [LocalDate] into a [String] representation for database storage.
     *
     * @param date The [LocalDate] to convert.
     * @return The string representation of the date in ISO-8601 format (yyyy-MM-dd).
     */
    @TypeConverter
    fun fromLocalDate(date: LocalDate): String = date.toString()

    /**
     * Converts a stored [String] from the database back into a [LocalDate].
     *
     * @param value The string value stored in the database.
     * @return The corresponding [LocalDate] parsed from the string.
     */
    @TypeConverter
    fun toLocalDate(value: String): LocalDate = LocalDate.parse(value)
}
