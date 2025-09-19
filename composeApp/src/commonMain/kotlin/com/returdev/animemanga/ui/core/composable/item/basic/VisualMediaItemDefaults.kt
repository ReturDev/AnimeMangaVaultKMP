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
     * Default aspect ratio of media item images.
     */

    const val IMAGE_HEIGHT_ASPECT_RATIO = 0.67f

    /**
     * Default width of a media item card.
     */
    val itemWidth = 162.dp

    /**
     * Default rounded corner shape for media item cards.
     */
    val itemShape = RoundedCornerShape(8.dp)

    /**
     * Default rounded shape for scrim overlay.
     */
    val scrimShape = RoundedCornerShape(50)
}
