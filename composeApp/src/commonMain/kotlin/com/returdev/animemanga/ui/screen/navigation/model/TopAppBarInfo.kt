package com.returdev.animemanga.ui.screen.navigation.model


/**
 * Represents the configuration for a top app bar, including its title
 * and whether a back navigation icon should be displayed.
 *
 * @param title The text displayed as the app bar title, or `null` if no title should be shown.
 * @param showBackIcon Whether the app bar should display a back navigation icon.
 */
data class TopAppBarInfo(
    val title : String?,
    val showBackIcon : Boolean,
)
