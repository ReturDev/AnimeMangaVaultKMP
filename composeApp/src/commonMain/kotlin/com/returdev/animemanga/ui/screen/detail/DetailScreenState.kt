package com.returdev.animemanga.ui.screen.detail

import com.returdev.animemanga.ui.model.detailed.VisualMediaDetailedUi
import com.returdev.animemanga.ui.model.user.UserLibraryStatusUI

/**
 * Represents the UI state for the detail screen of a visual media item (anime or manga).
 *
 * @property visualMediaLibraryStatus The user's current library status for this media item,
 * or `null` if the item is not in the user's library.
 * @property visualMediaState The current loading/display state of the detailed media content.
 */
data class DetailScreenState(
    val visualMediaLibraryStatus : UserLibraryStatusUI? = null,
    val visualMediaState : VisualMediaState = VisualMediaState.Loading
) {

    /**
     * Represents the loading state of the detailed visual media data.
     */
    sealed class VisualMediaState {

        /**
         * Indicates that the media details are currently being loaded.
         */
        object Loading : VisualMediaState()

        /**
         * Indicates that the media details were successfully loaded.
         *
         * @param T A type extending [VisualMediaDetailedUi], representing the detailed data model.
         * @property visualMedia The fully loaded visual media details.
         */
        data class Loaded<T : VisualMediaDetailedUi>(
            val visualMedia : T,
        ) : VisualMediaState()

        /**
         * Indicates that an error occurred while loading the media details.
         *
         * @property message A descriptive error message explaining what went wrong.
         */
        data class Error(val message : String) : VisualMediaState()
    }
}
