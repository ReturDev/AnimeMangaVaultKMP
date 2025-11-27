package com.returdev.animemanga.ui.model.detailed

import com.returdev.animemanga.domain.model.anime.TrailerModel
import com.returdev.animemanga.domain.model.core.GenreModel
import com.returdev.animemanga.domain.model.core.ReleasedModel
import com.returdev.animemanga.ui.model.basic.AnimeBasicUi
import com.returdev.animemanga.ui.model.core.TitleUi

/**
 * Represents detailed information about an anime, including its basic info, trailer, titles,
 * scoring, ranking, synopsis, status, genres, demographics, source, episodes, and airing details.
 *
 * @property basicInfo The basic information of the anime.
 * @property trailer Optional trailer information for the anime.
 * @property extraTitles A list of alternative titles for the anime.
 * @property numberOfScorers The total number of users who scored/rated the anime.
 * @property rank The rank of the anime based on user scores or popularity.
 * @property synopsis A summary description of the anime's plot.
 * @property status The current status of the anime (e.g., "Airing", "Completed").
 * @property demographics A list of target audience genres for the anime (e.g., Shonen, Seinen).
 * @property genres A list of genres the anime belongs to (e.g., Action, Romance).
 * @property source The source material of the anime (e.g., Manga, Light Novel, Original).
 * @property episodes The total number of episodes.
 * @property airing Whether the anime is currently airing.
 * @property aired The released dates of the anime.
 * @property season The season in which the anime was/will be released (e.g., "Winter 2023").
 */
data class AnimeDetailedUi(
    override val basicInfo : AnimeBasicUi,
    val trailer : TrailerModel?,
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
