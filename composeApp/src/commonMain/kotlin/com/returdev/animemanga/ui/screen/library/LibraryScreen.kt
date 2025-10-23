package com.returdev.animemanga.ui.screen.library

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.returdev.animemanga.core.roompaging.RoomPagerState
import com.returdev.animemanga.ui.core.composable.item.basic.BasicVisualMediaItem
import com.returdev.animemanga.ui.core.composable.list.grid.VisualMediaGridDefaults
import com.returdev.animemanga.ui.model.basic.VisualMediaBasicUi
import com.returdev.animemanga.ui.model.core.MediaCategory
import com.returdev.animemanga.ui.model.user.UserLibraryStatusUI
import com.returdev.animemanga.ui.screen.library.composable.toggle.CategorySwitch
import org.koin.compose.viewmodel.koinViewModel

/**
 * Main screen for displaying the user's media library.
 *
 * Allows switching between Anime/Manga categories and filtering items
 * based on their status (e.g., Watching, Completed, etc.).
 *
 * @param modifier Optional modifier for styling.
 * @param navigateToItemDetail Callback invoked when a media item is clicked.
 */
@Composable
fun LibraryScreen(
    modifier : Modifier = Modifier,
    navigateToItemDetail : (Int, MediaCategory) -> Unit
) {
    val viewModel = koinViewModel<LibraryViewModel>()
    val state by viewModel.uiState.collectAsState()

    val categorySelected = remember { mutableStateOf(MediaCategory.ANIME) }
    val statusSelected = remember { mutableStateOf(UserLibraryStatusUI.FOLLOWING) }

    LaunchedEffect(categorySelected.value, statusSelected.value) {
        when (categorySelected.value) {
            MediaCategory.ANIME -> viewModel.getAnimesByStatus(statusSelected.value)
            MediaCategory.MANGA -> viewModel.getMangasByStatus(statusSelected.value)
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {

        CategorySwitch(
            modifier = Modifier.padding(16.dp),
            state = categorySelected,
            onSelectedChange = { categorySelected.value = it }
        )


        LibraryStatusTabs(
            modifier = Modifier.fillMaxWidth(),
            currentStatusSelected = statusSelected.value,
            onChangeStatusSelected = { statusSelected.value = it }
        )


        LibraryContent(
            modifier = Modifier.weight(1f),
            pagerStateData = when (categorySelected.value) {
                MediaCategory.ANIME -> state.animeStatusData[statusSelected.value]!!
                MediaCategory.MANGA -> state.mangaStatusData[statusSelected.value]!!
            },
            onItemClick = { id -> navigateToItemDetail(id, categorySelected.value) }
        )
    }
}

/**
 * Displays the horizontal row of tabs representing different library statuses.
 *
 * @param currentStatusSelected Currently selected status.
 * @param onChangeStatusSelected Callback to update status when a tab is clicked.
 */
@Composable
private fun LibraryStatusTabs(
    modifier : Modifier = Modifier,
    currentStatusSelected : UserLibraryStatusUI,
    onChangeStatusSelected : (UserLibraryStatusUI) -> Unit
) {
    ScrollableTabRow(
        modifier = modifier,
        selectedTabIndex = currentStatusSelected.ordinal,
        edgePadding = 0.dp
    ) {
        UserLibraryStatusUI.entries.forEach { status ->
            Tab(
                text = { Text(text = status.getText()) },
                selected = status == currentStatusSelected,
                onClick = { onChangeStatusSelected(status) }
            )
        }
    }
}

/**
 * Renders the content of the screen depending on the pager state:
 * - Shows a grid of items if content is loaded.
 * - Shows a loading indicator while data is being fetched.
 *
 * @param pagerStateData Data that includes the pager state and scroll behavior.
 * @param onItemClick Callback triggered when a media item is clicked.
 */
@Composable
private fun LibraryContent(
    modifier : Modifier = Modifier,
    pagerStateData : LibraryScreenState.LibraryScreenData,
    onItemClick : (Int) -> Unit
) {
    when (val pagerState = pagerStateData.pagerState) {
        is RoomPagerState.Loaded<VisualMediaBasicUi> -> {
            LazyVerticalGrid(
                modifier = modifier.fillMaxWidth(),
                columns = VisualMediaGridDefaults.columns,
                contentPadding = PaddingValues(4.dp),
                state = pagerStateData.scrollState
            ) {
                items(
                    count = pagerState.items.size,
                    key = { pagerState.items[it].id }
                ) { index ->
                    val item = pagerState.items[index]
                    BasicVisualMediaItem(
                        imageUrl = item.image.url,
                        title = item.title,
                        type = item.type.typeName,
                        onClick = { onItemClick(item.id) }
                    )
                }
            }
        }

        RoomPagerState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}