package com.returdev.animemanga.ui.screen.navigation.model

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.returdev.animemanga.ui.model.core.MediaCategory
import com.returdev.animemanga.ui.screen.navigation.model.Destination.ShowMore.SECTION
import com.returdev.animemanga.ui.screen.navigation.model.Destination.VisualMediaDetails.ID
import com.returdev.animemanga.ui.screen.navigation.model.Destination.VisualMediaDetails.MEDIA_CATEGORY
import com.returdev.animemanga.ui.screen.showmore.model.ShowMoreSection


/**
 * Represents a navigation destination within the application.
 *
 * This sealed class defines a structure for declaring routes along with optional path
 * parameters used for navigation in a type-safe way. Each destination holds a base
 * [route] and can specify one or more dynamic path parameters. The final template route
 * (e.g., `"details/{id}/{category}"`) is generated automatically.
 *
 * Subclasses implement [getArguments] to define the required navigation arguments.
 *
 * @property route The base route string without parameters (e.g., `"details"`).
 * @param pathParams Optional list of path parameter names used in the route template.
 */
sealed class Destination(
    protected val route : String,
    vararg pathParams : String
) {

    /**
     * The full route template including parameter placeholders, such as:
     * `"visual_media_details/{id}/{media_category}"`.
     */
    val templateRoute = buildTemplateRoute(route, pathParams)

    /**
     * Returns the list of navigation arguments required for this destination.
     *
     * Each subclass provides a concrete set of [NamedNavArgument] items that match its
     * expected path parameters.
     */
    abstract fun getArguments() : List<NamedNavArgument>

    /**
     * Represents a destination that does not require any navigation arguments.
     *
     * @param route The base route string.
     */
    sealed class NoArgumentDestination(route : String) : Destination(route) {

        override fun getArguments() : List<NamedNavArgument> = emptyList()

        /** Home screen destination. */
        object Home : NoArgumentDestination("home")

        /** Anime search screen destination. */
        object AnimeSearch : NoArgumentDestination("anime_search")

        /** Manga search screen destination. */
        object MangaSearch : NoArgumentDestination("manga_search")

        /** Library screen destination. */
        object Library : NoArgumentDestination("library")
    }

    /**
     * Navigation destination for displaying detailed information about a visual media item.
     *
     * Expects two parameters:
     * - [ID]: The unique identifier of the media item.
     * - [MEDIA_CATEGORY]: The media category (anime or manga).
     */
    object VisualMediaDetails : Destination(
        route = "visual_media_details",
        "id", "media_category"
    ) {

        const val ID = "id"
        const val MEDIA_CATEGORY = "media_category"

        override fun getArguments() : List<NamedNavArgument> = listOf(
            navArgument(name = ID) { type = NavType.StringType },
            navArgument(name = MEDIA_CATEGORY) { type = NavType.StringType }
        )

        /**
         * Builds a complete navigation route for this destination.
         *
         * @param id The media item's identifier.
         * @param category The category of the media (anime or manga).
         * @return A fully resolved route string (e.g., `"visual_media_details/42/ANIME"`).
         */
        fun buildRoute(id : Int, category : MediaCategory) : String {
            return route.withParams(id.toString(), category.name)
        }
    }

    /**
     * Destination for screens that show an extended list of content (e.g., "Show More").
     *
     * Expects one parameter:
     * - [SECTION]: The section name indicating what is being expanded.
     */
    object ShowMore : Destination(
        route = "show_more",
        "section"
    ) {

        const val SECTION = "section"

        override fun getArguments() : List<NamedNavArgument> = listOf(
            navArgument(name = SECTION) { type = NavType.StringType }
        )

        /**
         * Builds a complete navigation route for this destination.
         *
         * @param section The enumerated section value.
         * @return A fully resolved route string (e.g., `"show_more/POPULAR"`).
         */
        fun buildRoute(section : ShowMoreSection) : String {
            return route.withParams(section.name)
        }
    }

    /**
     * Builds a route template by appending all given path parameter placeholders.
     *
     * @param route The base route.
     * @param params The list of parameter names.
     * @return A template route containing placeholder segments.
     */
    private fun buildTemplateRoute(route : String, params : Array<out String>) : String {
        return if (params.isEmpty()) route else {
            buildString {
                append(route)
                params.forEach { append("/{$it}") }
            }
        }
    }

    /**
     * Appends the provided arguments to a route in the correct order.
     *
     * @receiver The base route string.
     * @param args The list of argument values to append.
     * @return A full resolved route string with arguments applied.
     */
    protected fun String.withParams(vararg args : Any?) : String {
        return buildString {
            append(this@withParams)
            args.forEach { arg -> arg?.let { append("/$it") } }
        }
    }
}
