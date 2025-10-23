package com.returdev.animemanga.ui.screen.library.composable.toggle

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Provides default values and helpers for configuring [CategorySwitch] colors.
 */
object CategorySwitchDefaults {

    /**
     * Returns a [CategorySwitchColors] instance with default colors from the current Material theme.
     *
     * @param backgroundColor The color of the switch background (default: MaterialTheme.surface)
     * @param onBackgroundColor The color of the text/labels on the background (default: MaterialTheme.onSurface)
     * @param thumbColor The color of the sliding thumb (default: MaterialTheme.primary)
     * @param onThumbColor The color of the text/labels on the thumb (default: MaterialTheme.onPrimary)
     *
     * @return [CategorySwitchColors] configured with the specified or default colors.
     */
    @Composable
    fun colors(
        backgroundColor: Color = MaterialTheme.colorScheme.surface,
        onBackgroundColor: Color = MaterialTheme.colorScheme.onSurface,
        thumbColor: Color = MaterialTheme.colorScheme.primary,
        onThumbColor: Color = MaterialTheme.colorScheme.onPrimary
    ): CategorySwitchColors {
        return CategorySwitchColors(
            backgroundColor,
            onBackgroundColor,
            thumbColor,
            onThumbColor
        )
    }

}

/**
 * Immutable data class representing the colors used by a [CategorySwitch].
 *
 * @property backgroundColor The background color of the switch.
 * @property onBackgroundColor The color of the text/labels displayed on the background.
 * @property thumbColor The color of the sliding thumb.
 * @property onThumbColor The color of the text/labels displayed on top of the thumb.
 */
@Immutable
data class CategorySwitchColors(
    val backgroundColor: Color,
    val onBackgroundColor: Color,
    val thumbColor: Color,
    val onThumbColor: Color
)
