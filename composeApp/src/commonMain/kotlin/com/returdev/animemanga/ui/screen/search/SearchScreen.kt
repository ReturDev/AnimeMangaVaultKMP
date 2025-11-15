package com.returdev.animemanga.ui.screen.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import animemangavaultkmp.composeapp.generated.resources.Res
import animemangavaultkmp.composeapp.generated.resources.ic_arrow_back
import animemangavaultkmp.composeapp.generated.resources.ic_close
import animemangavaultkmp.composeapp.generated.resources.ic_no_search_result
import animemangavaultkmp.composeapp.generated.resources.ic_search
import animemangavaultkmp.composeapp.generated.resources.search_hint_text
import animemangavaultkmp.composeapp.generated.resources.search_no_results_found
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import com.returdev.animemanga.ui.core.composable.list.grid.VisualMediaGrid
import com.returdev.animemanga.ui.core.composable.list.grid.VisualMediaLoadingGrid
import com.returdev.animemanga.ui.model.basic.VisualMediaBasicUi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

/**
 * A generic search screen for visual media.
 *
 * @param T The type of the visual media items, which must extend [VisualMediaBasicUi].
 * @param modifier The modifier to be applied to the screen.
 * @param navigateToDetailScreen A lambda function to navigate to the detail screen of an item.
 * @param viewModel The ViewModel for the search screen.
 */
@Composable
fun <T : VisualMediaBasicUi> SearchScreen(
    modifier : Modifier = Modifier,
    navigateToDetailScreen : (Int) -> Unit,
    viewModel : SearchViewModel<T>
) {

    val state by viewModel.uiState.collectAsState()
    val query = remember { mutableStateOf("") }

    val items = state.items.collectAsLazyPagingItems()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        SearchBarComposable(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            query = query,
            lastSearch = state.lastSearchQuery,
            onQueryChange = { query.value = it },
            onSearch = { searchQuery ->

                viewModel.search(searchQuery)

            },
            onCancelQueryWrite = {
                query.value = state.lastSearchQuery
            }
        )

        SearchScreenContent(
            modifier = Modifier.fillMaxSize(),
            items = items,
            onItemClick = navigateToDetailScreen
        )
    }

}

/**
 * A composable that provides a search bar for the search screen.
 *
 * @param modifier The modifier to be applied to the search bar.
 * @param query The current search query.
 * @param lastSearch The last search query performed.
 * @param onQueryChange A lambda function to be called when the search query changes.
 * @param onSearch A lambda function to be called when a search is performed.
 * @param onCancelQueryWrite A lambda function to be called when the query writing is canceled.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBarComposable(
    modifier : Modifier = Modifier,
    query : State<String>,
    lastSearch : String,
    onQueryChange : (String) -> Unit,
    onSearch : (String) -> Unit,
    onCancelQueryWrite : () -> Unit
) {

    var isExpanded by remember { mutableStateOf(false) }

    SearchBar(
        modifier = modifier.padding(16.dp),
        inputField = {
            SearchBarDefaults.InputField(
                query = if (isExpanded) query.value else lastSearch,
                onQueryChange = onQueryChange,
                expanded = isExpanded,
                onExpandedChange = { isExpanded = it },
                onSearch = {
                    onSearch(it)
                    isExpanded = false
                },
                placeholder = {
                    Text(text = stringResource(Res.string.search_hint_text))
                },
                leadingIcon = {
                    SearchBarLeadingIcon(
                        isExpanded = isExpanded,
                        onClickBack = {
                            isExpanded = false
                            onCancelQueryWrite()
                        }
                    )
                },
                trailingIcon = {
                    SearchBarTrailingIcon(
                        isExpanded = isExpanded,
                        onClickClear = { onQueryChange("") },
                        onClickSearch = {
                            onSearch(query.value)
                            isExpanded = false
                        }
                    )
                }
            )
        },
        expanded = isExpanded,
        onExpandedChange = {
            isExpanded = it
        },
        content = {
            Box(Modifier.background(Color.Red).height(50.dp).fillMaxWidth())
        }
    )

}

/**
 * The leading icon for the search bar, which is an arrow back icon.
 *
 * @param isExpanded Whether the search bar is expanded.
 * @param onClickBack A lambda function to be called when the back button is clicked.
 */
@Composable
fun SearchBarLeadingIcon(
    isExpanded : Boolean,
    onClickBack : () -> Unit
) {

    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        visible = isExpanded
    ) {
        IconButton(
            onClick = onClickBack
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_arrow_back),
                contentDescription = null
            )
        }
    }

}

/**
 * The trailing icon for the search bar, which includes a clear and a search button.
 *
 * @param isExpanded Whether the search bar is expanded.
 * @param onClickClear A lambda function to be called when the clear button is clicked.
 * @param onClickSearch A lambda function to be called when the search button is clicked.
 */
@Composable
fun SearchBarTrailingIcon(
    isExpanded : Boolean,
    onClickClear : () -> Unit,
    onClickSearch : () -> Unit
) {

    Row {

        AnimatedVisibility(
            enter = fadeIn(),
            exit = fadeOut(),
            visible = isExpanded
        ) {
            IconButton(
                onClick = onClickClear
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_close),
                    contentDescription = null
                )
            }

        }

        IconButton(
            onClick = onClickSearch,
            colors = IconButtonDefaults.iconButtonColors(
                disabledContentColor = IconButtonDefaults.iconButtonColors().contentColor
            ),
            enabled = isExpanded
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_search),
                contentDescription = null
            )
        }
    }

}

/**
 * The content of the search screen, which displays the search results.
 *
 * @param T The type of the visual media items.
 * @param modifier The modifier to be applied to the content.
 * @param items The lazy paging items to be displayed.
 * @param onItemClick A lambda function to be called when an item is clicked.
 */
@Composable
private fun <T : VisualMediaBasicUi> SearchScreenContent(
    modifier: Modifier = Modifier,
    items: LazyPagingItems<T>,
    onItemClick: (Int) -> Unit
) {

    val transition = rememberInfiniteTransition()

    when {
        items.loadState.refresh is LoadState.NotLoading && items.itemCount == 0 -> {
            AnimatedContent(
                modifier = modifier,
                targetState = items.itemCount,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                }
            ) { _ ->
                EmptySearch(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

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

/**
 * A composable that is displayed when a search returns no results.
 *
 * @param modifier The modifier to be applied to the composable.
 */
@Composable
private fun EmptySearch(
    modifier : Modifier = Modifier
) {

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            16.dp,
            Alignment.CenterVertically
        )
    ) {

        Card (
            modifier = Modifier.fillMaxWidth(0.35f).aspectRatio(1f),
            shape = CircleShape
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_no_search_result),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(0.6f)
                )
            }

        }

        Text(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            text = stringResource(Res.string.search_no_results_found),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )

    }


}
