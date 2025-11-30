package com.returdev.animemanga.ui.screen.detail.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.returdev.animemanga.ui.model.core.TitleUi

/**
 * Displays the main content section of a detail screen, including alternative titles,
 * additional information labels, and optional content displayed after the titles section.
 *
 * @param modifier Optional [Modifier] for layout customization.
 * @param titles A list of alternative or additional titles for the media item.
 * @param info A list of key–value information pairs to display (e.g., "Episodes" to "12").
 * @param postTitlesContent Optional composable block displayed after titles and info sections.
 */
@Composable
fun DetailScreenContent(
    modifier : Modifier = Modifier,
    titles : List<TitleUi>,
    info : List<Pair<String, String>>,
    postTitlesContent : (@Composable () -> Unit)? = null
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {

        TitlesSection(
            titles = titles
        )

        InfoSection(
            info = info
        )

        postTitlesContent?.invoke()
    }
}

/**
 * Displays a list of alternative titles if available.
 *
 * @param titles The list of titles to display.
 */
@Composable
private fun TitlesSection(
    titles : List<TitleUi>
) {

    if (titles.isNotEmpty()) {

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            titles.forEach { title ->
                Title(title)
            }
        }
    }
}

/**
 * Displays a single title entry with its type.
 *
 * @param title The title UI model containing the type and value.
 */
@Composable
private fun Title(
    title : TitleUi
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text("${title.titleType}:")
        Text(modifier = Modifier.weight(1f), text = title.title)
    }
}

/**
 * Displays a section of informational items arranged in rows with two items per row.
 *
 * @param modifier Optional [Modifier] for layout customization.
 * @param info The list of information pairs (label to value).
 */
@Composable
private fun InfoSection(
    modifier : Modifier = Modifier,
    info : List<Pair<String, String>>
) {
    FlowRow(
        modifier = modifier,
        maxItemsInEachRow = 2,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        info.forEach { (title, value) ->
            InfoItem(
                modifier = Modifier.weight(1f),
                title = title,
                value = value
            )
        }
    }
}

/**
 * Displays a single labeled information item.
 *
 * @param modifier Optional [Modifier] for layout customization.
 * @param title The descriptive label (e.g., "Episodes").
 * @param value The value associated with the label.
 */
@Composable
fun InfoItem(
    modifier : Modifier,
    title : String,
    value : String
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = value,
            fontWeight = FontWeight.Bold
        )
    }
}

