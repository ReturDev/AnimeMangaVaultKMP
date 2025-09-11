package com.returdev.animemanga.ui.core.composable.item.basic

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

/**
 * Holds default styling and layout values used for visual media items
 *
 * This ensures consistency in how media items are displayed
 * (dimensions, shapes, ratios, etc.).
 */
object VisualMediaItemDefaults {

    /**
     * Default width of a media item card.
     */
    val ItemWidth = 162.dp

    /**
     * Default aspect ratio of media item images.
     */
    const val HeightAspectRatio = 0.67f

    /**
     * Default rounded corner shape for media item cards.
     */
    val ItemShape = RoundedCornerShape(8.dp)

    /**
     * Default rounded shape for scrim overlay.
     */
    val ScrimShape = RoundedCornerShape(50)
}
