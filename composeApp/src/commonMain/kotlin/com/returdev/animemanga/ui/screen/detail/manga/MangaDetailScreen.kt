package com.returdev.animemanga.ui.screen.detail.manga

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.returdev.animemanga.ui.screen.detail.composables.CommonDetailScreen
import org.koin.compose.viewmodel.koinViewModel

/**
 * Displays the detail screen for a specific manga identified by [mangaId].
 *
 * @param modifier Optional [Modifier] applied to the root composable.
 * @param mangaId The unique identifier of the manga whose detailed information
 * should be retrieved and displayed.
 */
@Composable
fun MangaDetailScreen(
    modifier : Modifier = Modifier,
    mangaId : Int,
) {

    val viewModel = koinViewModel<MangaDetailViewModel>()
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getMangaDetails(mangaId)
    }

    CommonDetailScreen(
        modifier = modifier,
        uiState = uiState.value,
        onLibraryStatusChange = { newStatus ->
            viewModel.updateMangaLibraryStatus(mangaId, newStatus)
        }
    )

}