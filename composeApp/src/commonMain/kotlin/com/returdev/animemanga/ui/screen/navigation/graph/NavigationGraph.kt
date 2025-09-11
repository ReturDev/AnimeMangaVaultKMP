package com.returdev.animemanga.ui.screen.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.returdev.animemanga.ui.screen.navigation.model.Destination

/**
 * Sets up the navigation graph for the app using Jetpack Compose Navigation.
 *
 * This function defines all the composable destinations/screens and their routes.
 *
 * @param modifier Modifier for styling or layout adjustments.
 * @param controller The [NavHostController] used to manage navigation between screens.
 */
@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    controller: NavHostController
) {
    NavHost(
        navController = controller,
        startDestination = Destination.Home.route
    ) {
        // Home screen destination
        composable(Destination.Home.route) { }

        // Anime search screen destination
        composable(Destination.AnimeSearch.route) { }

        // Manga search screen destination
        composable(Destination.MangaSearch.route) { }

        // User library screen destination
        composable(Destination.Library.route) { }

        // Visual media details screen destination
        composable(Destination.VisualMediaDetails.route) { }
    }
}
