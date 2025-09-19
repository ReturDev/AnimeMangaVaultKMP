package com.returdev.animemanga.ui.core.composable.list.row

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.cash.paging.compose.LazyPagingItems
import com.returdev.animemanga.ui.core.composable.item.basic.BasicVisualMediaItem
import com.returdev.animemanga.ui.model.basic.VisualMediaBasicUi

/**
 * Enum representing different sections on the Home screen
 * where a "Show More" action can navigate the user.
 *
 * @property title The string resource ID used as the section title.
 * @property category The type of media (ANIME or MANGA) for this section.
 */
@Composable
fun <T : VisualMediaBasicUi> VisualMediaRow(
    modifier : Modifier = Modifier,
    items : LazyPagingItems<T>,
    onItemClick : (Int) -> Unit
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(VisualMediaRowDefaults.spaceBetweenItems),
        contentPadding = VisualMediaRowDefaults.contentPadding
    ) {
        items(count = items.itemCount, key = { items[it]?.id ?: it }) { index ->
            items[index]?.let { item ->
                BasicVisualMediaItem(
                    imageUrl = item.image.url,
                    title = item.title,
                    rate = item.score,
                    type = item.type.typeName,
                    onClick = { onItemClick(item.id) }
                )
            }
        }
    }
}
