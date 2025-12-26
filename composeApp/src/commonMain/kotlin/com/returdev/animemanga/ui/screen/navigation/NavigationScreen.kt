package com.returdev.animemanga.ui.screen.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import animemangavaultkmp.composeapp.generated.resources.Res
import animemangavaultkmp.composeapp.generated.resources.ic_nav_back
import animemangavaultkmp.composeapp.generated.resources.nav_back_button_content_description
import com.returdev.animemanga.ui.screen.navigation.graph.NavigationGraph
import com.returdev.animemanga.ui.screen.navigation.model.BottomNavigationItem
import com.returdev.animemanga.ui.screen.navigation.model.TopAppBarInfo
import org.jetbrains.compose.resources.painterResource
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

    var showBottomBar by remember { mutableStateOf(true) }
    var topAppBarInfo by remember { mutableStateOf<TopAppBarInfo?>(null) }
    var navigationItemSelected by remember {
        mutableStateOf<BottomNavigationItem>(
            BottomNavigationItem.Home
        )
    }

    LaunchedEffect(backStackEntry) {
        val currentRoute = backStackEntry?.destination?.route
        showBottomBar = currentRoute in bottomBarItems.map { it.destination.templateRoute }
        if (showBottomBar) {
            navigationItemSelected =
                bottomBarItems.first { it.destination.templateRoute == currentRoute }
        }
    }



    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                NavigationBottomBar(
                    navController = navController,
                    itemSelected = navigationItemSelected
                )
            }
        },
        topBar = {

            topAppBarInfo?.let {
                TopBar(
                    title = it.title,
                    showBackIcon = it.showBackIcon,
                    onBackClick = { navController.popBackStack() }
                )
            }

        }
    ) { paddingValues ->
        NavigationGraph(
            modifier = Modifier.padding(paddingValues),
            controller = navController,
            setTopAppBarInfo = { topAppBarInfo = it }
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
    itemSelected : BottomNavigationItem
) {

    BottomAppBar {
        bottomBarItems.forEach { item ->
            NavigationBarItem(
                icon = item.iconComp,
                label = {
                    Text(
                        text = stringResource(item.label),
                    )
                },
                alwaysShowLabel = false,
                selected = item == itemSelected,
                onClick = { navController.navigate(item.destination.templateRoute) },

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
    title : String?,
    showBackIcon : Boolean,
    onBackClick : () -> Unit
) {

    TopAppBar(
        title = {
            title?.let {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.StartEllipsis,
                    style = MaterialTheme.typography.displaySmall
                )
            }
        },
        navigationIcon = {
            if (showBackIcon) {
                IconButton(
                    onClick = onBackClick
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_nav_back),
                        contentDescription = stringResource(Res.string.nav_back_button_content_description)
                    )
                }
            }
        }
    )
}

