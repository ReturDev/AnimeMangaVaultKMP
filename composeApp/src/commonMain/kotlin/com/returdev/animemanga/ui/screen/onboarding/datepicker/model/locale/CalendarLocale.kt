package com.returdev.animemanga.ui.screen.onboarding.datepicker.model.locale

import androidx.compose.material3.CalendarLocale
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

/**
 * Provides the default [CalendarLocale] used by the application.
 *
 * @return The default [CalendarLocale] appropriate for the current platform and user settings.
 */
@Composable
@ReadOnlyComposable
expect fun getDefaultCalendarLocale(): CalendarLocale
