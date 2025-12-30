package com.returdev.animemanga.ui.screen.onboarding.datepicker.model.locale

import androidx.compose.material3.CalendarLocale
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import java.util.Locale

@Composable @ReadOnlyComposable actual fun getDefaultCalendarLocale() : CalendarLocale = Locale.getDefault()