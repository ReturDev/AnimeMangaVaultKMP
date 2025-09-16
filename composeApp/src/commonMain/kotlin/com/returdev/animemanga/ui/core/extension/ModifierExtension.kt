package com.returdev.animemanga.ui.core.extension

import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * Adds a shimmer effect to any [Modifier].
 *
 * This uses an [InfiniteTransition] to animate a linear gradient that sweeps across the
 * composable, giving the appearance of a "loading shimmer".
 *
 * @param transition An [InfiniteTransition] used to drive the shimmer animation.
 * @param brushColors A list of [Color]s used in the shimmer gradient.
 *                    Defaults to [defaultShimmerColors] (light gray shades).
 *
 * @return A [Modifier] that applies the shimmer effect.
 */
fun Modifier.shimmerBrush(
    transition: InfiniteTransition,
    brushColors: List<Color> = defaultShimmerColors
) = composed {

    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1200,
                easing = LinearEasing
            )
        )
    )

    this.drawWithCache {

            val width = size.width
            val height = size.height

            val startX = -width + ((width * 2f) * progress)
            val endX = 0f + ((width * 2f) * progress)

            onDrawBehind {
                val brush = Brush.linearGradient(
                    colors = brushColors,
                    start = Offset(x = startX, y = height),
                    end = Offset(x = endX, y = height)
                )
                drawRect(brush = brush)
            }
        }
}

/**
 * Default shimmer gradient colors.
 *
 * A sequence of light gray shades with varying alpha to create the shimmer illusion.
 */
private val defaultShimmerColors = listOf(
    Color.LightGray.copy(alpha = 0.7f),
    Color.LightGray.copy(alpha = 0.3f),
    Color.LightGray.copy(alpha = 0.7f)
)
