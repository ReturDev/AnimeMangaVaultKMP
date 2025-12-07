package com.returdev.animemanga.ui.screen.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.returdev.animemanga.ui.model.core.MediaCategory
import com.returdev.animemanga.ui.screen.detail.anime.AnimeDetailScreen
import com.returdev.animemanga.ui.screen.detail.manga.MangaDetailScreen

/**
 * Displays the appropriate detailed screen for a visual media item based on its category.
 *
 * @param modifier Optional [Modifier] for adjusting the layout of the screen.
 * @param id The unique identifier of the media item to display.
 * @param category The type of media (anime or manga) used to determine which detail screen to render.
 */
@Composable
fun DetailScreen(
    modifier : Modifier = Modifier,
    id : Int,
    category : MediaCategory
) {

    when (category) {
        MediaCategory.ANIME -> {
            AnimeDetailScreen(
                modifier = modifier,
                animeId = id
            )
        }

        MediaCategory.MANGA -> {
            MangaDetailScreen(
                modifier = modifier,
                mangaId = id,
            )
        }
    }
}