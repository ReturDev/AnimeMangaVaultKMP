package com.returdev.animemanga.ui.screen.showmore.model

import androidx.compose.runtime.Composable
import animemangavaultkmp.composeapp.generated.resources.Res
import animemangavaultkmp.composeapp.generated.resources.anime_current_season_title
import animemangavaultkmp.composeapp.generated.resources.top_anime_title
import animemangavaultkmp.composeapp.generated.resources.top_manga_title
import com.returdev.animemanga.ui.model.core.MediaCategory
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * Enum representing different sections on the Home screen
 * where a "Show More" action can navigate the user.
 *
 * @property title The string resource ID used as the section title.
 * @property category The type of media (ANIME or MANGA) for this section.
 */
enum class ShowMoreSection(
    private val title: StringResource,
    val category: MediaCategory
) {
    TOP_ANIME(
        Res.string.top_anime_title,
        MediaCategory.ANIME
    ),

    TOP_MANGA(
        Res.string.top_manga_title,
        MediaCategory.MANGA
    ),

    CURRENT_SEASON(
        Res.string.anime_current_season_title,
        MediaCategory.ANIME
    );

    /**
     * Composable function to retrieve the localized title string
     * for this section.
     *
     * @return The localized string for the section title.
     */
    @Composable
    fun getTitle(): String {
        return stringResource(title)
    }
}
