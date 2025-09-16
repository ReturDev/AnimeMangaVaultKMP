package com.returdev.animemanga.ui.screen.home

import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import animemangavaultkmp.composeapp.generated.resources.Res
import animemangavaultkmp.composeapp.generated.resources.anime_current_season_title
import animemangavaultkmp.composeapp.generated.resources.error_loading_content
import animemangavaultkmp.composeapp.generated.resources.ic_refresh
import animemangavaultkmp.composeapp.generated.resources.retry_button_text
import animemangavaultkmp.composeapp.generated.resources.show_more_button_text
import animemangavaultkmp.composeapp.generated.resources.top_anime_title
import animemangavaultkmp.composeapp.generated.resources.top_manga_title
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import com.returdev.animemanga.core.format
import com.returdev.animemanga.ui.core.composable.item.basic.BasicVisualMediaItem
import com.returdev.animemanga.ui.core.composable.item.basic.loading.BasicVisualMediaLoadingRow
import com.returdev.animemanga.ui.model.basic.VisualMediaBasicUi
import com.returdev.animemanga.ui.model.core.MediaCategory
import com.returdev.animemanga.ui.screen.showmore.model.ShowMoreSection
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

// Constants controlling layout sizes and spacing
private val sectionContentHeight = 300.dp
private val spaceBetweenItems = 4.dp

/**
 * Main Home screen displaying top anime, current season anime, and top manga.
 *
 * @param modifier Modifier applied to the root layout.
 * @param navigateToShowMore Callback for navigating to "Show More" screens (e.g., top anime list).
 * @param navigateToItemDetail Callback for navigating to a detail screen for a specific item.
 */
@Composable
fun HomeScreen(
    modifier : Modifier = Modifier,
    navigateToShowMore : (ShowMoreSection) -> Unit,
    navigateToItemDetail : (Int, MediaCategory) -> Unit
) {
    val viewModel = koinViewModel<HomeViewModel>()
    val state = viewModel.uiState.collectAsState()

    val infiniteTransition = rememberInfiniteTransition()

    val topAnime = state.value.topAnime.collectAsLazyPagingItems()
    val animeCurrentSeason = state.value.animeCurrentSeason.collectAsLazyPagingItems()
    val topManga = state.value.topManga.collectAsLazyPagingItems()

    // Scrollable vertical layout holding multiple sections
    Column(
        modifier = modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp)
    ) {
        // Top Anime section
        MediaSection(
            title = stringResource(Res.string.top_anime_title),
            items = topAnime,
            infiniteTransition = infiniteTransition,
            onShowMore = { navigateToShowMore(ShowMoreSection.TOP_ANIME) },
            onRetryClick = { viewModel.getTopAnime() },
            onItemClick = { id -> navigateToItemDetail(id, MediaCategory.ANIME) }
        )
        // Current Season Anime section
        MediaSection(
            title = stringResource(Res.string.anime_current_season_title),
            items = animeCurrentSeason,
            infiniteTransition = infiniteTransition,
            onShowMore = { navigateToShowMore(ShowMoreSection.CURRENT_SEASON) },
            onRetryClick = { viewModel.getAnimeCurrentSeason()},
            onItemClick = { id -> navigateToItemDetail(id, MediaCategory.ANIME) }
        )
        // Top Manga section
        MediaSection(
            title = stringResource(Res.string.top_manga_title),
            items = topManga,
            infiniteTransition = infiniteTransition,
            onShowMore = { navigateToShowMore(ShowMoreSection.TOP_MANGA) },
            onRetryClick = { viewModel.getTopManga() },
            onItemClick = { id -> navigateToItemDetail(id, MediaCategory.MANGA) }
        )
    }
}

/**
 * A reusable UI section that displays a list of visual media.
 * Handles states: content, loading shimmer, and error.
 */
@Composable
private fun <T : VisualMediaBasicUi> MediaSection(
    modifier : Modifier = Modifier,
    title : String,
    items : LazyPagingItems<T>,
    infiniteTransition : InfiniteTransition,
    onShowMore : () -> Unit,
    onRetryClick : () -> Unit,
    onItemClick : (Int) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        SectionHeader(title = title, onShowMore = onShowMore)

        when {
            // Content is available
            items.itemCount != 0 -> {
                MediaListRow(items = items, onItemClick = onItemClick)
            }
            // Loading state with shimmer placeholders
            items.loadState.refresh is LoadState.Loading && items.itemCount == 0 -> {
                BasicVisualMediaLoadingRow(
                    modifier = Modifier.fillMaxWidth().height(sectionContentHeight),
                    transition = infiniteTransition,
                    spaceBetweenItems = spaceBetweenItems
                )
            }
            // Error state with retry button
            items.loadState.refresh is LoadState.Error -> {
                ErrorStateCard(
                    retryButtonVisible = true,
                    onRetryClick = onRetryClick
                )
            }
        }
    }
}

/**
 * Horizontally scrolling row that displays a list of media items.
 */
@Composable
private fun <T : VisualMediaBasicUi> MediaListRow(
    modifier : Modifier = Modifier,
    items : LazyPagingItems<T>,
    onItemClick : (Int) -> Unit
) {
    LazyRow(
        modifier = modifier.fillMaxWidth().height(sectionContentHeight),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spaceBetweenItems),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
    ) {
        items(count = items.itemCount, key = { items[it]?.id ?: it }) { index ->
            items[index]?.let { item ->
                BasicVisualMediaItem(
                    imageUrl = item.image.url,
                    title = item.title,
                    rate = "%.2f".format(item.score),
                    type = item.type.typeName,
                    onClick = { onItemClick(item.id) }
                )
            }
        }
    }
}

/**
 * Section header with title and optional "Show More" button.
 */
@Composable
private fun SectionHeader(
    modifier : Modifier = Modifier,
    title : String,
    onShowMore : (() -> Unit)?
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
        onShowMore?.let {
            TextButton(onClick = onShowMore) {
                Text(
                    text = stringResource(Res.string.show_more_button_text),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * Card shown in case of error with an optional retry button.
 */
@Composable
private fun ErrorStateCard(
    retryButtonVisible : Boolean,
    onRetryClick : () -> Unit
) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth().height(sectionContentHeight)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
        ) {
            Text(text = stringResource(Res.string.error_loading_content))
            if (retryButtonVisible) {
                RetryButton(onClick = onRetryClick)
            }
        }
    }
}

/**
 * A retry button with an icon and text.
 */
@Composable
private fun RetryButton(
    modifier : Modifier = Modifier,
    onClick : () -> Unit
) {
    TextButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_refresh),
                contentDescription = null
            )
            Text(text = stringResource(Res.string.retry_button_text))
        }
    }
}
