package com.returdev.animemanga.ui.screen.onboarding.datepicker.model

/**
 * Represents a change in a specific field of a date input.
 *
 * @property value The new value entered for the date field.
 */
sealed class DateFieldChanged(val value : String) {

    /** Represents a change in the day field of a date. */
    class Day(value : String) : DateFieldChanged(value)

    /** Represents a change in the month field of a date. */
    class Month(value : String) : DateFieldChanged(value)

    /** Represents a change in the year field of a date. */
    class Year(value : String) : DateFieldChanged(value)

}
