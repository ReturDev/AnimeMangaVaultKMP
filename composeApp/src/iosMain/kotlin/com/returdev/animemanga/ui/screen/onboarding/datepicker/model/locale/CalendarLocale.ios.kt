package com.returdev.animemanga.ui.screen.onboarding.datepicker.model.locale

import androidx.compose.material3.CalendarLocale
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import platform.Foundation.NSLocale
import platform.Foundation.preferredLanguages

@Composable
@ReadOnlyComposable
actual fun getDefaultCalendarLocale() : CalendarLocale = NSLocale(
    NSLocale.preferredLanguages.first() as String
)