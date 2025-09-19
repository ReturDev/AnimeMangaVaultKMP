package com.returdev.animemanga.ui.core.composable.list.row

import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.returdev.animemanga.ui.core.composable.item.basic.loading.BasicVisualMediaPlaceholder

/**
 * A horizontal row of shimmering placeholders representing visual media items while the
 * actual data is loading from the network or database.
 **
 * @param modifier Modifier applied to the Row container.
 * @param transition InfiniteTransition instance used to animate the shimmer effect.
 */
@Composable
fun VisualMediaLoadingRow(
    modifier: Modifier = Modifier,
    transition: InfiniteTransition
) {

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(VisualMediaRowDefaults.spaceBetweenItems),
        contentPadding = VisualMediaRowDefaults.contentPadding,
        userScrollEnabled = false
    ) {

        items(count = Int.MAX_VALUE) {
            BasicVisualMediaPlaceholder(
                modifier = Modifier.fillMaxHeight(),
                transition = transition
            )
        }

    }

}