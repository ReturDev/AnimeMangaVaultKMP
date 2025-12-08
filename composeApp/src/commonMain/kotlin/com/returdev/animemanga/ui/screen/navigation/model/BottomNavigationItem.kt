package com.returdev.animemanga.ui.screen.navigation.model

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import animemangavaultkmp.composeapp.generated.resources.Res
import animemangavaultkmp.composeapp.generated.resources.ic_anime
import animemangavaultkmp.composeapp.generated.resources.ic_home
import animemangavaultkmp.composeapp.generated.resources.ic_library
import animemangavaultkmp.composeapp.generated.resources.ic_manga
import animemangavaultkmp.composeapp.generated.resources.nav_label_anime
import animemangavaultkmp.composeapp.generated.resources.nav_label_home
import animemangavaultkmp.composeapp.generated.resources.nav_label_library
import animemangavaultkmp.composeapp.generated.resources.nav_label_manga
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource

/**
 * Represents an item in the bottom navigation bar.
 *
 * Each item defines its navigation [destination], associated [icon], and [label].
 * Provides a reusable composable [iconComp] to render the icon in the UI.
 *
 * @property destination The navigation target associated with this item.
 * @property icon The drawable resource used as the icon.
 * @property label The string resource used as the label.
 */
sealed class BottomNavigationItem(
    val destination: Destination,
    private val icon: DrawableResource,
    val label: StringResource
) {

    /**
     * Composable function to display the icon for this navigation item.
     */
    val iconComp = @Composable {
        Icon(
            painter = painterResource(icon),
            contentDescription = null
        )
    }

    /** Bottom navigation item for the Home screen. */
    object Home : BottomNavigationItem(
        destination = Destination.NoArgumentDestination.Home,
        icon = Res.drawable.ic_home,
        label = Res.string.nav_label_home
    )

    /** Bottom navigation item for the Anime Search screen. */
    object AnimeSearch : BottomNavigationItem(
        destination = Destination.NoArgumentDestination.AnimeSearch,
        icon = Res.drawable.ic_anime,
        label = Res.string.nav_label_anime
    )

    /** Bottom navigation item for the Manga Search screen. */
    object MangaSearch : BottomNavigationItem(
        destination = Destination.NoArgumentDestination.MangaSearch,
        icon = Res.drawable.ic_manga,
        label = Res.string.nav_label_manga
    )

    /** Bottom navigation item for the Library screen. */
    object Library : BottomNavigationItem(
        destination = Destination.NoArgumentDestination.Library,
        icon = Res.drawable.ic_library,
        label = Res.string.nav_label_library
    )

}
