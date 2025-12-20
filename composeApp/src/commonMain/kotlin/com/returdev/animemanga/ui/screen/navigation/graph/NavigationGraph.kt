package com.returdev.animemanga.ui.screen.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import animemangavaultkmp.composeapp.generated.resources.Res
import animemangavaultkmp.composeapp.generated.resources.nav_label_anime
import animemangavaultkmp.composeapp.generated.resources.nav_label_home
import animemangavaultkmp.composeapp.generated.resources.nav_label_library
import animemangavaultkmp.composeapp.generated.resources.nav_label_manga
import com.returdev.animemanga.ui.model.core.MediaCategory
import com.returdev.animemanga.ui.screen.detail.DetailScreen
import com.returdev.animemanga.ui.screen.home.HomeScreen
import com.returdev.animemanga.ui.screen.library.LibraryScreen
import com.returdev.animemanga.ui.screen.navigation.model.Destination
import com.returdev.animemanga.ui.screen.navigation.model.TopAppBarInfo
import com.returdev.animemanga.ui.screen.search.anime.AnimeSearchScreen
import com.returdev.animemanga.ui.screen.search.manga.MangaSearchScreen
import com.returdev.animemanga.ui.screen.showmore.ShowMoreScreen
import com.returdev.animemanga.ui.screen.showmore.model.ShowMoreSection
import org.jetbrains.compose.resources.stringResource

/**
 * Sets up the navigation graph for the app using Jetpack Compose Navigation.
 *
 * @param modifier Modifier for styling or layout adjustments.
 * @param controller The [NavHostController] used to manage navigation between screens.
 */
@Composable
fun NavigationGraph(
    modifier : Modifier = Modifier,
    controller : NavHostController,
    setTopAppBarInfo : (TopAppBarInfo) -> Unit
) {
    NavHost(
        navController = controller,
        startDestination = Destination.NoArgumentDestination.Home.templateRoute
    ) {

        composable(Destination.NoArgumentDestination.Home.templateRoute) {

            setTopAppBarInfo(
                TopAppBarInfo(
                    title = stringResource(Res.string.nav_label_home),
                    showBackIcon = false
                )
            )

            HomeScreen(
                modifier = modifier,
                navigateToItemDetail = controller::navigateToItemDetail,
                navigateToShowMore = controller::navigateToShowMore
            )

        }

        composable(Destination.NoArgumentDestination.AnimeSearch.templateRoute) {

            setTopAppBarInfo(
                TopAppBarInfo(
                    title = stringResource(Res.string.nav_label_anime),
                    showBackIcon = false
                )
            )

            AnimeSearchScreen(
                modifier = modifier,
                navigateToDetailScreen = controller::navigateToItemDetail
            )

        }

        composable(Destination.NoArgumentDestination.MangaSearch.templateRoute) {

            setTopAppBarInfo(
                TopAppBarInfo(
                    title = stringResource(Res.string.nav_label_manga),
                    showBackIcon = false
                )
            )

            MangaSearchScreen(
                modifier = modifier,
                navigateToDetailScreen = controller::navigateToItemDetail
            )

        }

        composable(Destination.NoArgumentDestination.Library.templateRoute) {

            setTopAppBarInfo(
                TopAppBarInfo(
                    title = stringResource(Res.string.nav_label_library),
                    showBackIcon = false
                )
            )

            LibraryScreen(
                modifier = modifier,
                navigateToItemDetail = controller::navigateToItemDetail
            )

        }

        composable(Destination.VisualMediaDetails.templateRoute) { navEntry ->

            setTopAppBarInfo(
                TopAppBarInfo(
                    title = null,
                    showBackIcon = true
                )
            )

            DetailScreen(
                modifier = modifier,
                id = navEntry.getArgument(Destination.VisualMediaDetails.ID) !!,
                category = navEntry.getArgument(Destination.VisualMediaDetails.MEDIA_CATEGORY) !!
            )


        }

        composableWithArguments(Destination.ShowMore) { navEntry ->

            val showMoreSection = ShowMoreSection.valueOf(
                navEntry.getArgument(Destination.ShowMore.SECTION) !!
            )

            setTopAppBarInfo(
                TopAppBarInfo(
                    title = showMoreSection.getTitle(),
                    showBackIcon = true
                )
            )

            ShowMoreScreen(
                modifier = modifier,
                section = showMoreSection,
                navigateToItemDetail = controller::navigateToItemDetail
            )

        }
    }
}

fun NavGraphBuilder.composableWithArguments(
    destination : Destination,
    content : @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = destination.templateRoute,
        arguments = destination.getArguments(),
        content = { content(it) }
    )
}

private fun <T> NavBackStackEntry.getArgument(key : String) : T? {
    return this.savedStateHandle[key]
}

private fun NavHostController.navigateToItemDetail(
    id : Int,
    mediaCategory : MediaCategory
) {

    this.navigate(Destination.VisualMediaDetails.buildRoute(id, mediaCategory))

}

private fun NavHostController.navigateToShowMore(
    section : ShowMoreSection
) {
    this.navigate(Destination.ShowMore.buildRoute(section))
}
