package com.returdev.animemanga.ui.model.user

import androidx.compose.runtime.Composable
import animemangavaultkmp.composeapp.generated.resources.Res
import animemangavaultkmp.composeapp.generated.resources.visual_media_state_completed
import animemangavaultkmp.composeapp.generated.resources.visual_media_state_dropped
import animemangavaultkmp.composeapp.generated.resources.visual_media_state_favourites
import animemangavaultkmp.composeapp.generated.resources.visual_media_state_following
import animemangavaultkmp.composeapp.generated.resources.visual_media_state_on_hold
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * Represents the different statuses a user can assign to an anime or manga in their library.
 *
 * @property textRes The string resource ID used to display the status in the UI.
 */
enum class UserLibraryStatusUI(
    private val textRes: StringResource
) {

    FOLLOWING(Res.string.visual_media_state_following),
    FAVOURITES(Res.string.visual_media_state_favourites),
    COMPLETED(Res.string.visual_media_state_completed),
    ON_HOLD(Res.string.visual_media_state_on_hold),
    DROPPED(Res.string.visual_media_state_dropped);

    /**
     * Returns the localized string for this status using Compose's [stringResource].
     *
     * @return The human-readable string representing the status.
     */
    @Composable
    fun getText(): String = stringResource(textRes)

}
