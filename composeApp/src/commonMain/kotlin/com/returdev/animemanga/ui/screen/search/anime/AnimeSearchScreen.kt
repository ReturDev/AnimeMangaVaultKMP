package com.returdev.animemanga.ui.screen.search.anime

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.returdev.animemanga.ui.model.core.MediaCategory
import com.returdev.animemanga.ui.screen.search.SearchScreen
import org.koin.compose.viewmodel.koinViewModel

/**
 * A composable screen that allows the user to search for anime.
 *
 * This screen uses the [SearchScreen] composable to provide the search functionality,
 * and the [AnimeSearchViewModel] to handle the business logic.
 *
 * @param modifier The modifier to be applied to the screen.
 * @param navigateToDetailScreen A lambda function to navigate to the detail screen of an anime.
 */
@Composable
fun AnimeSearchScreen(
    modifier : Modifier = Modifier,
    navigateToDetailScreen: (Int, MediaCategory ) -> Unit,
) {

    val viewModel: AnimeSearchViewModel = koinViewModel()

    SearchScreen(
        modifier = modifier,
        navigateToDetailScreen = { id -> navigateToDetailScreen(id, MediaCategory.ANIME) },
        viewModel = viewModel
    )

}
