package com.returdev.animemanga.ui.core.composable.list.grid

import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.returdev.animemanga.ui.core.composable.item.basic.VisualMediaItemDefaults
import com.returdev.animemanga.ui.core.composable.item.basic.loading.BasicVisualMediaPlaceholder

/**
 * A grid layout showing shimmering placeholder items while visual media content is loading.
 *
 * This composable mimics the layout of a real [VisualMediaGrid] but displays placeholders
 * with a shimmer effect to indicate loading state. It is ideal for paginated content that
 * has not yet been fetched from a repository or API.
 *
 * @param modifier Modifier to customize the grid container.
 * @param transition The [InfiniteTransition] used to animate the shimmer effect for each placeholder.
 */
@Composable
fun VisualMediaLoadingGrid(
    modifier : Modifier = Modifier,
    transition : InfiniteTransition
) {

    LazyVerticalGrid(
        modifier = modifier,
        columns = VisualMediaGridDefaults.columns,
        horizontalArrangement = Arrangement.spacedBy(VisualMediaGridDefaults.spaceBetweenItems),
        verticalArrangement = Arrangement.spacedBy(VisualMediaGridDefaults.spaceBetweenItems),
        contentPadding = VisualMediaGridDefaults.contentPadding,
        userScrollEnabled = false
    ) {

        items(count = Int.MAX_VALUE) {
            BasicVisualMediaPlaceholder(
                modifier = Modifier.aspectRatio(0.6f),
                transition = transition
            )
        }

    }

}