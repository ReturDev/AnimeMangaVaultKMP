package com.returdev.animemanga.ui.screen.navigation.model

/**
 * Represents the different navigation destinations/screens in the app.
 *
 * Each subclass defines a unique [route] string used for navigation.
 */
sealed class Destination(
    val route: String
) {

    /** Home screen of the application. */
    object Home : Destination("home")

    /** Screen for searching anime. */
    object AnimeSearch : Destination("anime_search")

    /** Screen for searching manga. */
    object MangaSearch : Destination("manga_search")

    /** User library screen. */
    object Library : Destination("library")

    /** Detailed view screen for a specific visual media (anime/manga). */
    object VisualMediaDetails : Destination("visual_media_details")

}
