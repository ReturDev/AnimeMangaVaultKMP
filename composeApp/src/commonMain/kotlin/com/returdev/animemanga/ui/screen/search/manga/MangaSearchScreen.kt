package com.returdev.animemanga.ui.screen.search.manga

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.returdev.animemanga.ui.model.core.MediaCategory
import com.returdev.animemanga.ui.screen.search.SearchScreen

/**
 * A composable screen that allows the user to search for manga.
 *
 * This screen uses the [SearchScreen] composable to provide the search functionality,
 * and the [MangaSearchViewModel] to handle the business logic.
 *
 * @param modifier The modifier to be applied to the screen.
 * @param navigateToDetailScreen A lambda function to navigate to the detail screen of a manga.
 */
@Composable
fun MangaSearchScreen(
    modifier : Modifier = Modifier,
    navigateToDetailScreen : (Int, MediaCategory) -> Unit
) {

    val viewModel : MangaSearchViewModel = viewModel()

    SearchScreen(
        modifier = modifier,
        navigateToDetailScreen = { id -> navigateToDetailScreen(id, MediaCategory.MANGA)},
        viewModel = viewModel
    )

}