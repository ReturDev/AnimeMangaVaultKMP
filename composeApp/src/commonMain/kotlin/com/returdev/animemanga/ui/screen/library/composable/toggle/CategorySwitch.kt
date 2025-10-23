package com.returdev.animemanga.ui.screen.library.composable.toggle

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.graphics.Color
import com.returdev.animemanga.ui.model.core.MediaCategory

/**
 * A toggle switch component for switching between two categories (ANIME / MANGA).
 *
 * @param modifier Modifier to style the switch.
 * @param state A [State] representing the currently selected category.
 * @param colors Colors used for the thumb and background.
 * @param onSelectedChange Callback invoked when the selected category changes.
 */
@Composable
fun CategorySwitch(
    modifier: Modifier = Modifier,
    state: State<MediaCategory>,
    colors: CategorySwitchColors = CategorySwitchDefaults.colors(),
    onSelectedChange: (MediaCategory) -> Unit
) {

    val floatAnimation = animateFloatAsState(
        targetValue = if (state.value == MediaCategory.ANIME) 0f else 1f,
        animationSpec = tween(500, easing = FastOutSlowInEasing)
    )

    val interactionSource = remember { MutableInteractionSource() }


    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(50))
            .background(Color.LightGray)
            .padding(4.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {

                onSelectedChange(
                    when (state.value) {
                        MediaCategory.ANIME -> MediaCategory.MANGA
                        MediaCategory.MANGA -> MediaCategory.ANIME
                    }
                )
            }
    ) {

        CategorySwitchThumb(
            modifier = Modifier.fillMaxSize(),
            colors = colors,
            floatAnimation = floatAnimation
        )


        CategorySwitchLabels(
            modifier = Modifier.fillMaxSize(),
            colors = colors,
            floatAnimation = floatAnimation
        )
    }
}

/**
 * Draws the sliding thumb for the [CategorySwitch].
 *
 * @param modifier Modifier for size and layout.
 * @param colors Colors used for the thumb.
 * @param floatAnimation Float animation controlling the thumb position.
 */
@Composable
private fun CategorySwitchThumb(
    modifier: Modifier = Modifier,
    colors: CategorySwitchColors,
    floatAnimation: State<Float>
) {
    Canvas(modifier = modifier) {
        val rectSize = Size(size.width / 2, size.height)
        val topLeft = Offset((size.width * floatAnimation.value) / 2, 0f)

        drawRoundRect(
            color = colors.thumbColor,
            topLeft = topLeft,
            size = rectSize,
            cornerRadius = CornerRadius(size.height / 2, size.height / 2)
        )
    }
}

/**
 * Draws the labels for the categories inside the switch.
 *
 * @param modifier Modifier for layout.
 * @param colors Colors for label text and masking.
 * @param floatAnimation Float animation controlling the thumb position.
 */
@Composable
private fun CategorySwitchLabels(
    modifier: Modifier = Modifier,
    colors: CategorySwitchColors,
    floatAnimation: State<Float>
) {
    Row(
        modifier = modifier
            .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
            .drawWithContent {
                drawContent()

                val rectSize = Size(size.width / 2, size.height)
                val topLeft = Offset((size.width * floatAnimation.value) / 2, 0f)

                drawRoundRect(
                    color = colors.onThumbColor,
                    topLeft = topLeft,
                    size = rectSize,
                    cornerRadius = CornerRadius(size.height / 2, size.height / 2),
                    blendMode = BlendMode.SrcIn
                )
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Provide text color for labels
        CompositionLocalProvider(LocalContentColor provides colors.onBackgroundColor) {
            CategoryLabel(category = MediaCategory.ANIME)
            CategoryLabel(category = MediaCategory.MANGA)
        }
    }
}

/**
 * Composable for a single category label inside the switch.
 *
 * @param category The [MediaCategory] to display.
 */
@Composable
private fun RowScope.CategoryLabel(
    category: MediaCategory
) {
    Text(
        modifier = Modifier.weight(1f),
        text = category.name.lowercase().replaceFirstChar { it.uppercase() },
        textAlign = TextAlign.Center
    )
}
