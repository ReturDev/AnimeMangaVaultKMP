package com.returdev.animemanga.ui.screen.detail.anime

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import animemangavaultkmp.composeapp.generated.resources.Res
import animemangavaultkmp.composeapp.generated.resources.ic_play
import animemangavaultkmp.composeapp.generated.resources.trailer_play_content_description
import animemangavaultkmp.composeapp.generated.resources.trailer_thumbnail_content_description
import chaintech.videoplayer.host.MediaPlayerHost
import chaintech.videoplayer.model.VideoPlayerConfig
import chaintech.videoplayer.ui.youtube.YouTubePlayerComposable
import coil3.compose.AsyncImage
import com.returdev.animemanga.domain.model.anime.TrailerModel
import com.returdev.animemanga.ui.model.detailed.AnimeDetailedUi
import com.returdev.animemanga.ui.screen.detail.composables.CommonDetailScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


/**
 * Displays the anime detail screen for the anime identified by [animeId].
 *
 * @param modifier Optional [Modifier] applied to the root of the screen.
 * @param animeId The unique identifier of the anime whose details should be displayed.
 */
@Composable
fun AnimeDetailScreen(
    modifier : Modifier = Modifier,
    animeId : Int,
) {
    val viewModel = koinViewModel<AnimeDetailViewModel>()
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAnimeDetails(animeId)
    }

    CommonDetailScreen(
        modifier = modifier,
        uiState = uiState.value,
        onLibraryStatusChange = { newStatus ->
            viewModel.updateAnimeLibraryStatus(animeId = animeId, newStatus = newStatus)
        }
    ) { visualMedia ->

        TrailerSection(
            trailerModel = (visualMedia as AnimeDetailedUi).trailer
        )

    }


}



/**
 * A video player component for playing anime trailers from a given [videoUrl].
 *
 * @param videoUrl The URL of the video to be played.
 */
@Composable
private fun VideoPlayer(
    videoUrl : String,
) {

    val playerConfig = VideoPlayerConfig(
        showControls = true,
        isPauseResumeEnabled = true,
        isSeekBarVisible = false,
        isFastForwardBackwardEnabled = false,
        isSpeedControlEnabled = false,
        isFullScreenEnabled = false,
        isScreenLockEnabled = false,
        isZoomEnabled = false
    )


    val playerHost = remember {
        MediaPlayerHost(
            mediaUrl = videoUrl,
            isPaused = false,
            isLooping = false,
        )
    }

    YouTubePlayerComposable(
        modifier = Modifier.fillMaxSize(),
        playerHost = playerHost,
        playerConfig = playerConfig
    )

}

/**
 * Displays the trailer section for an anime.
 *
 * @param trailerModel The trailer data model, or `null` if no trailer exists.
 */
@Composable
private fun TrailerSection(
    trailerModel : TrailerModel?
) {

    var startVideo by remember { mutableStateOf(false) }

    trailerModel?.let { trailer ->

        Crossfade(
            targetState = startVideo,
            modifier = Modifier.fillMaxWidth().aspectRatio(1 + (6 / 9f))
        ) {
            if (it) {
                VideoPlayer(
                    videoUrl = trailer.videoUrl
                )
            } else {
                ThumbnailComponent(
                    thumbnailUrl = trailer.thumbnailImageUrl,
                ) {
                    startVideo = true
                }
            }
        }


    }

}

/**
 * Displays a clickable thumbnail image that triggers video playback.
 *
 * @param thumbnailUrl The image URL for the trailer thumbnail.
 * @param onPlayClick Callback invoked when the user taps the play button.
 */
@Composable
private fun ThumbnailComponent(
    thumbnailUrl : String,
    onPlayClick : () -> Unit
) {

    Box(
        contentAlignment = Alignment.Center
    ) {

        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = thumbnailUrl,
            contentDescription = stringResource(resource = Res.string.trailer_thumbnail_content_description),
            contentScale = ContentScale.Crop
        )

        IconButton(
            modifier = Modifier.size(width = 96.dp, height = 64.dp),
            onClick = onPlayClick,
            shape = RoundedCornerShape(16.dp),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = Color.Black.copy(alpha = 0.7f),
                contentColor = Color.White
            )
        ) {
            Icon(
                modifier = Modifier.fillMaxSize().padding(8.dp),
                painter = painterResource(Res.drawable.ic_play),
                contentDescription = stringResource(Res.string.trailer_play_content_description)
            )
        }

    }

}