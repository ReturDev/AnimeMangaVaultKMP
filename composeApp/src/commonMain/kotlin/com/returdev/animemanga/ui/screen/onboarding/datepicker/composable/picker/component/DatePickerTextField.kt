package com.returdev.animemanga.ui.screen.onboarding.datepicker.composable.picker.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import animemangavaultkmp.composeapp.generated.resources.Res
import animemangavaultkmp.composeapp.generated.resources.date_day_placeholder
import animemangavaultkmp.composeapp.generated.resources.date_month_placeholder
import animemangavaultkmp.composeapp.generated.resources.date_year_placeholder
import com.returdev.animemanga.ui.screen.onboarding.datepicker.model.DateFieldChanged
import com.returdev.animemanga.ui.screen.onboarding.datepicker.model.DateState
import com.returdev.animemanga.ui.screen.onboarding.datepicker.util.CustomDatePickerUtils
import com.returdev.animemanga.ui.screen.onboarding.getMaxDayOfMonth
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.stringResource

/**
 * A composable that displays a date input field with separate text fields for day, month, and year.
 *
 * The component validates input based on the maximum allowed date [maxDate] and minimum allowed year [minYear].
 * It ensures that day, month, and year values remain consistent when one field changes (e.g., adjusting the day
 * if the selected month has fewer days). Uses [CustomDateTextField] for each field.
 *
 * @param modifier Modifier to adjust the layout or appearance of the row containing date fields.
 * @param day Current day value as a string.
 * @param month Current month value as a string.
 * @param year Current year value as a string.
 * @param maxDate The maximum allowable date.
 * @param minYear The minimum allowable year.
 * @param onDayChange Lambda invoked when the day value changes.
 * @param onMonthChange Lambda invoked when the month value changes.
 * @param onYearChange Lambda invoked when the year value changes.
 * @param onDone Lambda invoked when the user finishes inputting the year field.
 */
@Composable
fun DatePickerTextField(
    modifier : Modifier = Modifier,
    day : String,
    month : String,
    year : String,
    maxDate : LocalDate,
    minYear : Int,
    onDayChange : (String) -> Unit,
    onMonthChange : (String) -> Unit,
    onYearChange : (String) -> Unit,
    onDone : () -> Unit
) {

    val focusManager = LocalFocusManager.current

    val currentMaxDay = remember { mutableStateOf(31) }
    val currentMaxMonth = remember { mutableStateOf(12) }


    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        DayTextField(
            day = day,
            maxDay = getMaxDayOfMonth(month, year),
            focusManager = focusManager,
            onDayChange = { newDay ->
                handleDateFieldChange(
                    dateFieldChanged = DateFieldChanged.Day(newDay),
                    day = day,
                    month = month,
                    year = year,
                    maxDate = maxDate,
                    currentMaxDay = currentMaxDay.value,
                    currentMaxMonth = currentMaxMonth.value,
                    onDayChange = onDayChange,
                    onMonthChange = onMonthChange,
                    onYearChange = onYearChange,
                    onMaxDayChange = { currentMaxDay.value = it },
                    onMaxMonthChange = { currentMaxMonth.value = it }
                )

            }
        )

        Text(
            text = "/"
        )

        MonthTextField(
            month = month,
            maxMonth = currentMaxMonth.value,
            focusManager = focusManager,
            onMonthChange = { newMonth ->
                handleDateFieldChange(
                    dateFieldChanged = DateFieldChanged.Month(newMonth),
                    day = day,
                    month = month,
                    year = year,
                    maxDate = maxDate,
                    currentMaxDay = currentMaxDay.value,
                    currentMaxMonth = currentMaxMonth.value,
                    onDayChange = onDayChange,
                    onMonthChange = onMonthChange,
                    onYearChange = onYearChange,
                    onMaxDayChange = { currentMaxDay.value = it },
                    onMaxMonthChange = { currentMaxMonth.value = it }
                )
            }
        )

        Text(
            text = "/"
        )

        YearTextField(
            year = year,
            maxYear = maxDate.year,
            minYear = minYear,
            focusManager = focusManager,
            onYearChange = { newYear ->
                handleDateFieldChange(
                    dateFieldChanged = DateFieldChanged.Year(newYear),
                    day = day,
                    month = month,
                    year = year,
                    maxDate = maxDate,
                    currentMaxDay = currentMaxDay.value,
                    currentMaxMonth = currentMaxMonth.value,
                    onDayChange = onDayChange,
                    onMonthChange = onMonthChange,
                    onYearChange = onYearChange,
                    onMaxDayChange = { currentMaxDay.value = it },
                    onMaxMonthChange = { currentMaxMonth.value = it }
                )
            },
            onDone = onDone
        )

    }

}

/**
 * A composable text field for entering the day portion of a date.
 *
 * @param modifier Modifier for layout adjustments.
 * @param day Current day value as string.
 * @param maxDay Maximum allowable day value based on month and year.
 * @param focusManager Focus manager for handling keyboard navigation.
 * @param onDayChange Lambda invoked when the day value changes and focus is lost.
 */
@Composable
private fun DayTextField(
    modifier : Modifier = Modifier,
    day : String,
    maxDay : Int,
    focusManager : FocusManager,
    onDayChange : (String) -> Unit
) {

    var dayInput by remember(day) { mutableStateOf(day) }
    var isFocused by remember { mutableStateOf(false) }
    val visualValue = if (! isFocused && dayInput.length == 1) "0$dayInput" else dayInput

    CustomDateTextField(
        modifier = modifier,
        focusManager = focusManager,
        value = visualValue,
        placeholder = stringResource(Res.string.date_day_placeholder),
        maxLength = 2,
        onValueChange = { newValue ->

            if (newValue.isNotEmpty() && newValue.toInt() > maxDay) {
                return@CustomDateTextField
            }

            dayInput = newValue
        },
        onFocusChange = { hasFocus ->
            isFocused = hasFocus
            if (! isFocused) {
                onDayChange(dayInput)
            }
        },
    )

}

/**
 * A composable text field for entering the month portion of a date.
 *
 * @param modifier Modifier for layout adjustments.
 * @param month Current month value as string.
 * @param maxMonth Maximum allowable month (usually 12 or dynamically adjusted).
 * @param focusManager Focus manager for keyboard handling.
 * @param onMonthChange Lambda invoked when the month value changes and focus is lost.
 */
@Composable
private fun MonthTextField(
    modifier : Modifier = Modifier,
    month : String,
    maxMonth : Int,
    focusManager : FocusManager,
    onMonthChange : (String) -> Unit
) {

    var monthInput by remember(month) { mutableStateOf(month) }
    var isFocused by remember { mutableStateOf(false) }
    val visualValue = if (! isFocused && monthInput.length == 1) "0$monthInput" else monthInput

    CustomDateTextField(
        modifier = modifier,
        focusManager = focusManager,
        value = visualValue,
        placeholder = stringResource(Res.string.date_month_placeholder),
        maxLength = 2,
        onValueChange = { newValue ->
            if (newValue.isNotEmpty() && newValue.toInt() > maxMonth) {
                return@CustomDateTextField
            }
            monthInput = newValue
        },
        onFocusChange = { hasFocus ->
            isFocused = hasFocus
            if (! isFocused) {
                onMonthChange(monthInput)
            }
        },
    )

}

/**
 * A composable text field for entering the year portion of a date.
 *
 * The input is validated to stay within the [minYear] and [maxYear] bounds.
 *
 * @param modifier Modifier for layout adjustments.
 * @param year Current year value as string.
 * @param maxYear Maximum allowable year.
 * @param minYear Minimum allowable year.
 * @param focusManager Focus manager for keyboard handling.
 * @param onYearChange Lambda invoked when the year value changes and focus is lost.
 * @param onDone Lambda invoked when the user finishes inputting the year and presses the "Done" action.
 */
@Composable
private fun YearTextField(
    modifier : Modifier = Modifier,
    year : String,
    maxYear : Int,
    minYear : Int,
    focusManager : FocusManager,
    onYearChange : (String) -> Unit,
    onDone : () -> Unit
) {

    val yearInput = remember(year) { mutableStateOf(year) }

    CustomDateTextField(
        modifier = modifier,
        focusManager = focusManager,
        value = yearInput.value,
        placeholder = stringResource(Res.string.date_year_placeholder),
        maxLength = 4,
        onValueChange = { newValue ->

            if (
                CustomDatePickerUtils.canPrefixFormValidYear(
                    yearPrefixString = newValue,
                    minYear = minYear,
                    maxYear = maxYear
                )
            ) {
                yearInput.value = newValue
            }


        },
        onFocusChange = {
            if (yearInput.value.isNotEmpty() && yearInput.value.length < 4) {
                yearInput.value = year
            } else if (yearInput.value.length == 4) {
                onYearChange(yearInput.value)
            }
        },
        onDone = {
            onDone()
            focusManager.clearFocus()
        }
    )

}

/**
 * Handles updating the date state when a single date field (day, month, or year) changes.
 *
 * This function ensures that the entire date remains valid and respects [maxDate] constraints.
 * It also updates dependent fields and maximum allowable values for day and month if needed.
 *
 * @param dateFieldChanged Enum representing which field (day, month, or year) was changed.
 * @param day Current day string.
 * @param month Current month string.
 * @param year Current year string.
 * @param currentMaxDay Maximum day value based on the current month/year.
 * @param currentMaxMonth Maximum month value based on the current year.
 * @param maxDate Maximum allowed date.
 * @param onDayChange Lambda invoked to update the day value.
 * @param onMonthChange Lambda invoked to update the month value.
 * @param onYearChange Lambda invoked to update the year value.
 * @param onMaxDayChange Lambda invoked to update the max day when month/year changes.
 * @param onMaxMonthChange Lambda invoked to update the max month when year changes.
 */
private fun handleDateFieldChange(
    dateFieldChanged : DateFieldChanged,
    day : String,
    month : String,
    year : String,
    currentMaxDay : Int,
    currentMaxMonth : Int,
    maxDate : LocalDate,
    onDayChange : (String) -> Unit,
    onMonthChange : (String) -> Unit,
    onYearChange : (String) -> Unit,
    onMaxDayChange : (Int) -> Unit,
    onMaxMonthChange : (Int) -> Unit
) {
    val dateAdjustments = CustomDatePickerUtils.adjustDateField(
        fieldChanged = dateFieldChanged,
        dateState = DateState(
            day = day,
            month = month,
            year = year,
            maxDay = currentMaxDay,
            maxMonth = currentMaxMonth
        ),
        maxDate = maxDate
    )

    dateAdjustments.day?.let { onDayChange(it) }
    dateAdjustments.month?.let { onMonthChange(it) }
    dateAdjustments.year?.let { onYearChange(it) }
    dateAdjustments.maxDay?.let { onMaxDayChange(it) }
    dateAdjustments.maxMonth?.let { onMaxMonthChange(it) }

}