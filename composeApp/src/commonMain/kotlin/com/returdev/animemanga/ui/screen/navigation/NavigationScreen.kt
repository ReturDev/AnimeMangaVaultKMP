package com.returdev.animemanga.ui.screen.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.returdev.animemanga.ui.screen.navigation.graph.NavigationGraph
import com.returdev.animemanga.ui.screen.navigation.model.BottomNavigationItem
import org.jetbrains.compose.resources.stringResource

private val bottomBarItems = listOf(
    BottomNavigationItem.Home,
    BottomNavigationItem.AnimeSearch,
    BottomNavigationItem.MangaSearch,
    BottomNavigationItem.Library
)

/**
 * The main navigation screen that hosts top bar, bottom navigation bar, and content.
 *
 * @param navController The [NavHostController] used for navigating between screens.
 */
@Composable
fun NavigationScreen(
    navController : NavHostController
) {

    val backStackEntry by navController.currentBackStackEntryAsState()
    var showAppBars by remember { mutableStateOf(true) }
    var navigationItemSelected by remember { mutableStateOf<BottomNavigationItem>(BottomNavigationItem.Home) }

    LaunchedEffect(backStackEntry) {
        val currentRoute = backStackEntry?.destination?.route
        val isBottomItemDestination = currentRoute in bottomBarItems.map { it.destination.route }
        showAppBars = isBottomItemDestination
        if (isBottomItemDestination){
            navigationItemSelected = bottomBarItems.first{ it.destination.route == currentRoute}
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showAppBars){
                NavigationBottomBar(
                    navController = navController,
                    itemSelected = navigationItemSelected
                )
            }
        },
        topBar = {
            if (showAppBars){
                TopBar(
                    title = stringResource(navigationItemSelected.label)
                )
            }
        }
    ) { paddingValues ->
        NavigationGraph(
            modifier = Modifier.padding(paddingValues),
            controller = navController
        )
    }

}

/**
 * Bottom navigation bar composable.
 *
 * @param navController The [NavController] used to navigate on item clicks.
 * @param itemSelected Currently selected bottom navigation item.
 */
@Composable
private fun NavigationBottomBar(
    navController : NavController,
    itemSelected  : BottomNavigationItem
) {

    BottomAppBar{
        bottomBarItems.forEachIndexed { index, item ->
            NavigationBarItem(
                icon =  item.iconComp,
                label = {
                    Text(
                        text = stringResource(item.label),
                    )
                },
                alwaysShowLabel = false,
                selected = item == itemSelected,
                onClick = { navController.navigate(item.destination.route)},

            )
        }
    }

}


/**
 * Top app bar composable displaying the current screen title.
 *
 * @param title The title text to display in the top bar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    title : String
) {

    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.displaySmall
            )
        }
    )
}

