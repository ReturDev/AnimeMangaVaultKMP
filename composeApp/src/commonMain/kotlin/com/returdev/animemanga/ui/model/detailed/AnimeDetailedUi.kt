package com.returdev.animemanga.ui.model.detailed

import com.returdev.animemanga.domain.model.anime.TrailerModel
import com.returdev.animemanga.domain.model.core.GenreModel
import com.returdev.animemanga.domain.model.core.ReleasedModel
import com.returdev.animemanga.domain.model.core.TitleModel
import com.returdev.animemanga.ui.model.basic.AnimeBasicUi
import com.returdev.animemanga.ui.model.core.TitleUi

/**
 * Represents the detailed UI model for an anime item.
 *
 * This class extends [VisualMediaDetailedUi] by adding anime-specific information
 * such as trailer data, episode count, source material, and airing details.
 *
 * @property basicInfo Basic UI information about the anime (title, image, type, score, etc.).
 * @property trailer Trailer metadata including video URL and preview thumbnails.
 * @property extraTitles A list of alternative or translated titles for the anime.
 * @property numberOfScorers The number of users who rated this anime.
 * @property rank The overall ranking position among all anime.
 * @property synopsis A detailed summary of the anime’s plot.
 * @property status The current status of the anime (e.g., "Airing", "Finished Airing").
 * @property demographics A list of demographic categories (e.g., Shounen, Seinen).
 * @property genres A list of genre categories (e.g., Action, Comedy).
 * @property source The source material the anime is adapted from (e.g., "Manga", "Light Novel").
 * @property episodes The number of episodes the anime contains (or is expected to contain).
 * @property airing Whether the anime is currently airing.
 * @property aired Date information regarding when the anime started and/or finished airing.
 * @property season The season during which the anime aired, if available (e.g., "Spring 2024").
 */
data class AnimeDetailedUi(
    override val basicInfo : AnimeBasicUi,
    val trailer : TrailerModel,
    override val extraTitles : List<TitleUi>,
    override val numberOfScorers: Long,
    override val rank: Int,
    override val synopsis: String,
    override val status: String,
    override val demographics : List<GenreModel>,
    override val genres : List<GenreModel>,
    val source: String,
    val episodes: Int,
    val airing: Boolean,
    val aired: ReleasedModel,
    val season: String?
) : VisualMediaDetailedUi()
