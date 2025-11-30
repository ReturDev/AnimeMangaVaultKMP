package com.returdev.animemanga.ui.screen.detail.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import animemangavaultkmp.composeapp.generated.resources.Res
import animemangavaultkmp.composeapp.generated.resources.ic_outline_star
import coil3.compose.AsyncImage
import com.returdev.animemanga.core.format
import com.returdev.animemanga.ui.model.detailed.VisualMediaDetailedUi
import org.jetbrains.compose.resources.painterResource


/**
 * Displays the header section of a detailed visual media screen.
 *
 * This includes the main image, title, score information, genres,
 * demographics, and synopsis of the media.
 *
 * @param visualMediaDetailedUi The detailed UI model containing
 * all header information for the media item.
 */
@Composable
fun DetailScreenHeader(
    visualMediaDetailedUi : VisualMediaDetailedUi
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VisualMediaImage(
            imageUrl = visualMediaDetailedUi.basicInfo.image.url
        )

        Column(
            Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            HeaderInfo(
                title = visualMediaDetailedUi.basicInfo.title,
                score = visualMediaDetailedUi.basicInfo.score,
                numberOfScorers = visualMediaDetailedUi.numberOfScorers,
                type = visualMediaDetailedUi.basicInfo.type.typeName,
                rank = visualMediaDetailedUi.rank,
                genres = visualMediaDetailedUi.genres.map { it.name },
                demographics = visualMediaDetailedUi.demographics.map { it.name }
            )

            Text(visualMediaDetailedUi.synopsis)
        }
    }
}

/**
 * Displays the main cover image of the visual media.
 *
 * @param modifier Optional modifier for layout customization.
 * @param imageUrl The URL of the image to be displayed.
 */
@Composable
private fun VisualMediaImage(
    modifier : Modifier = Modifier,
    imageUrl : String
) {
    AsyncImage(
        modifier = modifier.shadow(elevation = 16.dp).height(250.dp),
        model = imageUrl,
        contentDescription = null
    )
}

/**
 * Displays the main informational block of a media header,
 * including title, score, type, rank, genres, and demographics.
 *
 * @param modifier Optional modifier for layout customization.
 * @param title The media title.
 * @param score The average user score.
 * @param numberOfScorers Total number of users who rated the media.
 * @param type The media type (e.g., TV, Movie, Manga).
 * @param rank The ranking position.
 * @param genres List of genre labels.
 * @param demographics List of demographic labels.
 */
@Composable
private fun HeaderInfo(
    modifier : Modifier = Modifier,
    title : String,
    score : Float,
    numberOfScorers : Long,
    type : String,
    rank : Int,
    genres : List<String>,
    demographics : List<String>
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )

        HeaderInfoBar(
            score = score,
            numberOfScorers = numberOfScorers,
            type = type,
            rank = rank
        )

        GenresAndDemographicsInfo(
            genres = genres,
            demographics = demographics
        )
    }
}

/**
 * Displays a row containing the score, rank, and media type.
 *
 * @param score The media score.
 * @param numberOfScorers Number of scorers that contributed to the score.
 * @param type The media type label.
 * @param rank The ranking position.
 */
@Composable
private fun HeaderInfoBar(
    score : Float,
    numberOfScorers : Long,
    type : String,
    rank : Int
) {

    Row(
        modifier = Modifier.fillMaxWidth().height(intrinsicSize = IntrinsicSize.Min),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        ScoreInfo(score, numberOfScorers)

        VerticalDivider(Modifier.fillMaxHeight())

        Text("Rank: $rank")

        VerticalDivider(Modifier.fillMaxHeight())

        Text(type)
    }
}

/**
 * Displays score information with star icon, score value,
 * and formatted number of scorers.
 *
 * @param score The media score.
 * @param numberOfScorers Number of scorers contributing to the score.
 */
@Composable
private fun ScoreInfo(
    score : Float,
    numberOfScorers : Long
) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_outline_star),
            contentDescription = null,
        )

        Spacer(Modifier.width(4.dp))

        Text(
            text = score.toString(),
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = "(${formatNumberOfScorers(numberOfScorers)})",
            fontWeight = FontWeight.Light,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

/**
 * Displays genres and demographic tags in a responsive flow layout.
 *
 * @param genres List of genre labels.
 * @param demographics List of demographic labels.
 */
@Composable
fun GenresAndDemographicsInfo(
    genres : List<String>,
    demographics : List<String>
) {

    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        genres.forEach {
            GenreDemographicTag(it)
        }

        demographics.forEach {
            GenreDemographicTag(it)
        }
    }
}

/**
 * Displays a single tag representing a genre or demographic.
 *
 * @param value The label text to display inside the tag.
 */
@Composable
fun GenreDemographicTag(
    value : String
) {
    Surface(
        shape = RoundedCornerShape(50),
    ) {
        Text(
            modifier = Modifier.padding(8.dp, 2.dp),
            text = value
        )
    }
}

/**
 * Formats a large number of scorers into a compact readable form.
 *
 * Examples:
 * - 1,200 → "1.2K"
 * - 5,000,000 → "5.0M"
 * - 2,300,000,000 → "2.3B"
 *
 * @param scorers The number to format.
 * @return A compact string representation of the number.
 */
private fun formatNumberOfScorers(scorers : Long) : String {
    val format = "%.1f"
    val scorerDivision : Float
    val formattedScorers : String

    if (scorers >= 1_000_000_000) {
        scorerDivision = scorers / 1_000_000_000f
        formattedScorers = format.format(scorerDivision)
        return formattedScorers.plus("B")
    } else if (scorers >= 1_000_000) {
        scorerDivision = scorers / 1_000_000f
        formattedScorers = format.format(scorerDivision)
        return formattedScorers.plus("M")
    } else if (scorers >= 1_000) {
        scorerDivision = scorers / 1_000f
        formattedScorers = format.format(scorerDivision)
        return formattedScorers.plus("K")
    } else {
        return scorers.toString()
    }
}
