package com.returdev.animemanga.ui.core.composable.list.grid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import app.cash.paging.compose.LazyPagingItems
import com.returdev.animemanga.ui.core.composable.item.basic.BasicVisualMediaItem
import com.returdev.animemanga.ui.model.basic.VisualMediaBasicUi
import kotlinx.coroutines.flow.Flow

/**
 * A vertically scrolling grid layout for displaying visual media items (anime, manga, etc.).
 *
 * This composable uses a [LazyVerticalGrid] to efficiently render paginated lists of media items.
 * Each item is displayed using [BasicVisualMediaItem] in a two-column grid.
 *
 * @param modifier Modifier to be applied to the grid container.
 * @param items The paginated list of visual media items to display.
 * @param onItemClick Callback triggered when an item is clicked, providing the item ID.
 */
@Composable
fun <T : VisualMediaBasicUi> VisualMediaGrid(
    modifier : Modifier = Modifier,
    items : LazyPagingItems<T>,
    onItemClick : (Int) -> Unit
) {

    LazyVerticalGrid(
        modifier = modifier.fillMaxWidth(),
        columns = VisualMediaGridDefaults.columns,
        horizontalArrangement = Arrangement.spacedBy(VisualMediaGridDefaults.spaceBetweenItems),
        verticalArrangement = Arrangement.spacedBy(VisualMediaGridDefaults.spaceBetweenItems),
        contentPadding = PaddingValues(4.dp),

    ) {
        items(count = items.itemCount, key = { items[it]?.id ?: it }) {
            items[it]?.let { item ->
                BasicVisualMediaItem(
                    imageUrl = item.image.url,
                    title = item.title,
                    rate = item.score,
                    type = item.type.typeName,
                    onClick = {onItemClick(item.id)}
                )

            }
        }
    }

}