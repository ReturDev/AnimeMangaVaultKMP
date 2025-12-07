package com.returdev.animemanga.ui.screen.detail.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.returdev.animemanga.ui.model.detailed.VisualMediaDetailedUi
import com.returdev.animemanga.ui.model.user.UserLibraryStatusUI
import com.returdev.animemanga.ui.screen.detail.DetailScreenState

/**
 * Displays a common detail screen layout for any type of visual media (anime or manga),
 * including a header, titles, info sections, and optional extra content.
 *
 * @param modifier Optional [Modifier] for customizing the screen layout.
 * @param uiState The current state of the detail screen, including loading status,
 * error states, and loaded media data.
 * @param onLibraryStatusChange Callback invoked whenever the user's library status changes.
 * Receives `null` if the item is removed from the library.
 * @param extraContent Additional UI content to display below the main detail information,
 * useful for media-specific extra fields (e.g., episode list, chapters, trailer, etc.).
 * Receives the loaded [VisualMediaDetailedUi] as input.
 */
@Composable
fun CommonDetailScreen(
    modifier : Modifier = Modifier,
    uiState : DetailScreenState,
    onLibraryStatusChange : (UserLibraryStatusUI?) -> Unit,
    extraContent : @Composable (VisualMediaDetailedUi) -> Unit = {}
) {

    val scrollState = rememberScrollState()

    when (uiState.visualMediaState) {

        is DetailScreenState.VisualMediaState.Error -> {
            // TODO: Implement error UI
        }

        is DetailScreenState.VisualMediaState.Loaded<*> -> {
            val visualMedia = uiState.visualMediaState.visualMedia

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {

                DetailScreenHeader(
                    visualMediaDetailedUi = visualMedia,
                    currentUserLibraryStatus = uiState.visualMediaLibraryStatus,
                    onLibraryStatusChange = onLibraryStatusChange
                )

                Spacer(modifier = Modifier.height(16.dp))

                DetailScreenContent(
                    titles = visualMedia.extraTitles,
                    info = visualMedia.getInfoFields(),
                    postTitlesContent = { extraContent(visualMedia) }
                )
            }
        }

        DetailScreenState.VisualMediaState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
