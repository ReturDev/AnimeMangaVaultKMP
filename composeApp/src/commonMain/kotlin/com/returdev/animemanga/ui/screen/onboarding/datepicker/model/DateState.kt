package com.returdev.animemanga.ui.screen.onboarding.datepicker.model

/**
 * Represents the current state of a date input.
 *
 * @property day The current day value as a string, or null if unset.
 * @property month The current month value as a string, or null if unset.
 * @property year The current year value as a string, or null if unset.
 * @property maxDay The maximum allowed day for the current month and year, or null if unset.
 * @property maxMonth The maximum allowed month, typically constrained by a max date, or null if unset.
 */
data class DateState(
    val day : String? = null,
    val month : String? = null,
    val year : String? = null,
    val maxDay : Int? = null,
    val maxMonth : Int? = null
)
