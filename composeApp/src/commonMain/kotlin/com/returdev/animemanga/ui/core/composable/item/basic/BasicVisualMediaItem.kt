package com.returdev.animemanga.ui.core.composable.item.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import animemangavaultkmp.composeapp.generated.resources.Res
import animemangavaultkmp.composeapp.generated.resources.ic_outline_star
import animemangavaultkmp.composeapp.generated.resources.rate_icon_content_description
import coil3.compose.AsyncImage
import com.returdev.animemanga.core.format
import com.returdev.animemanga.ui.theme.OnScrimColor
import com.returdev.animemanga.ui.theme.ScrimColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

/**
 * A reusable composable that displays a media item (anime/manga/etc.)
 * with its cover image, title, type, and rating.
 *
 * This version automatically sets up a "rating badge" scrim (star + rating)
 * and displays the media type below the title.
 *
 * @param modifier Modifier for customizing layout/appearance.
 * @param imageUrl URL of the media cover image.
 * @param title Title of the media item.
 * @param rate Rating string (e.g., "8.7").
 * @param type Media type (e.g., "TV", "Movie").
 * @param onClick Action invoked when the item is clicked.
 */
@Composable
fun BasicVisualMediaItem(
    modifier : Modifier = Modifier,
    imageUrl : String,
    title : String,
    rate : Float,
    type : String,
    onClick : () -> Unit
) {
    BasicVisualMediaItem(
        modifier = modifier,
        imageUrl = imageUrl,
        title = title,
        onClick = onClick,
        scrimContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(Res.drawable.ic_outline_star),
                    contentDescription = stringResource(Res.string.rate_icon_content_description)
                )
                Text(
                    text = "%.2f".format(rate),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },

        content = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = type,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
            )
        }
    )
}

/**
 * A more generic media item card that allows injection of
 * custom scrim content (overlay on image) and custom body content
 * (below the title).
 *
 * @param modifier Modifier for customizing layout/appearance.
 * @param imageUrl URL of the media cover image.
 * @param title Title of the media item.
 * @param onClick Action invoked when the item is clicked.
 * @param scrimContent Composable overlay shown on top of the image.
 * @param content Optional composable shown below the title.
 */
@Composable
fun BasicVisualMediaItem(
    modifier : Modifier = Modifier,
    imageUrl : String,
    title : String,
    onClick : () -> Unit,
    scrimContent : @Composable () -> Unit,
    content : @Composable () -> Unit = {}
) {
    Column(
        modifier = modifier
            .width(VisualMediaItemDefaults.itemWidth)
            .clip(VisualMediaItemDefaults.itemShape)
            .clickable(onClick = onClick)
            .padding(6.dp),
    ) {

        ItemImage(
            modifier = Modifier.clip(VisualMediaItemDefaults.itemShape),
            imageUrl = imageUrl,
            scrimContent = scrimContent
        )

        Spacer(Modifier.height(8.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(Modifier.height(4.dp))

        content()
    }
}

/**
 * Displays the media image with a scrim overlay in the corner.
 */
@Composable
private fun ItemImage(
    modifier : Modifier = Modifier,
    imageUrl : String,
    scrimContent : @Composable () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopEnd
    ){
        Image(imageUrl = imageUrl)
        ImageScrim(content = scrimContent)
    }
}

/**
 * A reusable scrim background that hosts an overlay (e.g., rating badge).
 * Applies a rounded background with theme colors.
 */
@Composable
private fun ImageScrim(
    modifier : Modifier = Modifier,
    content : @Composable () -> Unit
){
    Box(
        modifier = modifier
            .padding(8.dp)
            .clip(VisualMediaItemDefaults.scrimShape)
            .background(color = ScrimColor),
    ){
        // Change local content color for proper contrast over scrim
        CompositionLocalProvider(LocalContentColor provides OnScrimColor) {
            Box(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
            ) {
                content()
            }
        }
    }
}

/**
 * Displays the media image using Coil's [AsyncImage].
 *
 * Fixed to a width of 150dp and aspect ratio of 0.67 (poster-like).
 */
@Composable
private fun Image(
    modifier : Modifier = Modifier,
    imageUrl : String
) {
    AsyncImage(
        modifier = modifier.fillMaxWidth()
            .aspectRatio(VisualMediaItemDefaults.IMAGE_HEIGHT_ASPECT_RATIO),
        model = imageUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}
