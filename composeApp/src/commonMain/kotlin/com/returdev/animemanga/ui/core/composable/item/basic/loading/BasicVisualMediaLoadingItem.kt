package com.returdev.animemanga.ui.core.composable.item.basic.loading

import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import com.returdev.animemanga.ui.core.composable.item.basic.VisualMediaItemDefaults
import com.returdev.animemanga.ui.core.extension.shimmerBrush

/**
 * A horizontal row of shimmering placeholders representing visual media items while the
 * actual data is loading from the network or database.
 *
 * This is typically used in a loading state for lists of anime/manga or other media cards.
 *
 * @param modifier Modifier applied to the Row container.
 * @param spaceBetweenItems Horizontal spacing between each placeholder item.
 * @param transition InfiniteTransition instance used to animate the shimmer effect.
 */
@Composable
fun BasicVisualMediaLoadingRow(
    modifier: Modifier = Modifier,
    spaceBetweenItems: Dp,
    transition: InfiniteTransition
) {
    Row(
        modifier = modifier.fillMaxWidth(), // Row takes full width
        horizontalArrangement = Arrangement.spacedBy(spaceBetweenItems) // Spacing between items
    ) {

        repeat(3) {
            BasicVisualMediaPlaceholder(transition = transition)
        }
    }
}

/**
 * A single shimmering placeholder representing a visual media item.
 *
 * Mimics the appearance (size, shape) of a real media item card and applies a shimmer effect
 * to indicate loading. The shimmer animation gives users a visual cue that content is being fetched.
 *
 * @param modifier Modifier to customize the Box container.
 * @param transition InfiniteTransition used to animate the shimmer effect.
 */
@Composable
fun BasicVisualMediaPlaceholder(
    modifier: Modifier = Modifier,
    transition: InfiniteTransition,
) {
    Box(
        modifier = modifier
            .width(VisualMediaItemDefaults.ItemWidth)
            .fillMaxHeight()
            .clip(VisualMediaItemDefaults.ItemShape)
            .shimmerBrush(transition = transition)
    )
}
