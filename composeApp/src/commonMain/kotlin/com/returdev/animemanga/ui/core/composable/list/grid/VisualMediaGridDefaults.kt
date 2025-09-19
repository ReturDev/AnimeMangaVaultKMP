package com.returdev.animemanga.ui.core.composable.list.grid

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.ui.unit.dp
import com.returdev.animemanga.ui.core.composable.item.basic.VisualMediaItemDefaults

object VisualMediaGridDefaults {
    val contentPadding = PaddingValues(16.dp)
    val spaceBetweenItems = 8.dp
    val columns = GridCells.Adaptive(VisualMediaItemDefaults.itemWidth)
}