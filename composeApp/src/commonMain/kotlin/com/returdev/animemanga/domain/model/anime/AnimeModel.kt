package com.returdev.animemanga.domain.model.anime

import com.returdev.animemanga.domain.model.core.GenreModel
import com.returdev.animemanga.domain.model.core.ReleasedModel
import com.returdev.animemanga.domain.model.core.TitleModel
import com.returdev.animemanga.domain.model.visualmedia.VisualMediaModel

/**
 * Data class representing an anime with detailed attributes.
 *
 * @property basicInfo Basic information about the anime (such as id, images, title, type, score).
 * @property trailer Information about the anime's trailer.
 * @property extraTitles Additional titles for the anime (e.g., in different languages or synonyms).
 * @property numberOfScorers The number of users who have scored/rated the anime.
 * @property rank The ranking position of the anime.
 * @property synopsis A brief summary or description of the anime.
 * @property status The current status of the anime (e.g., ongoing, completed).
 * @property demographics List of demographic genres associated with the anime.
 * @property genres List of genres associated with the anime.
 * @property source The source material from which the anime is adapted (e.g., manga, novel).
 * @property episodes The total number of episodes in the anime.
 * @property airing Indicates if the anime is currently airing.
 * @property aired The release period of the anime.
 * @property season The season in which the anime was released. Nullable if unknown.
 */
data class AnimeModel(
    override val basicInfo : AnimeBasicModel,
    val trailer : TrailerModel?,
    override val extraTitles : List<TitleModel>,
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
) : VisualMediaModel()