package com.returdev.animemanga.ui.screen.showmore

import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.LoadStates
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import com.returdev.animemanga.ui.core.composable.list.grid.VisualMediaGrid
import com.returdev.animemanga.ui.core.composable.list.grid.VisualMediaLoadingGrid
import com.returdev.animemanga.ui.model.basic.VisualMediaBasicUi
import com.returdev.animemanga.ui.model.core.MediaCategory
import com.returdev.animemanga.ui.screen.showmore.model.ShowMoreSection
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel


/**
 * Displays a screen listing more items for a selected section, such as
 * top anime, top manga, or current season titles.
 *
 * @param modifier Modifier to be applied to the root of the screen.
 * @param section The section whose items should be displayed.
 * @param navigateToItemDetail Callback invoked when an item is clicked,
 * with the item's ID and its associated [MediaCategory].
 */
@Composable
fun ShowMoreScreen(
    modifier : Modifier = Modifier,
    section : ShowMoreSection,
    navigateToItemDetail : (Int, MediaCategory) -> Unit
) {

    val viewModel = koinViewModel<ShowMoreViewModel>()
    val items = viewModel.uiState.collectAsLazyPagingItems()
    val transition = rememberInfiniteTransition()

    LaunchedEffect(Unit) {
        viewModel.init(section)
    }

    ShowMoreScreenContent(
        modifier = modifier.fillMaxSize(),
        items = items,
        transition = transition,
        onItemClick = { id ->
            navigateToItemDetail(id, section.category)
        }
    )
}

/**
 * Internal UI container responsible for rendering the loaded paginated
 * visual media items for the Show More screen.
 *
 * @param modifier Modifier to apply to the layout.
 * @param items The paginated list of visual media to render.
 * @param transition Infinite transition used for loading animations.
 * @param onItemClick Callback invoked when a grid item is clicked.
 */
@Composable
private fun ShowMoreScreenContent(
    modifier : Modifier = Modifier,
    items : LazyPagingItems<VisualMediaBasicUi>,
    transition : InfiniteTransition,
    onItemClick : (Int) -> Unit
) {

    when {
        items.loadState.refresh is LoadState.Loading && items.itemCount == 0 -> {
            VisualMediaLoadingGrid(
                modifier = modifier,
                transition = transition
            )
        }

        items.itemCount > 0 -> {
            VisualMediaGrid(
                modifier = modifier,
                items = items,
                onItemClick = onItemClick
            )
        }
    }
}

