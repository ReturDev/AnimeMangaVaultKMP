package com.returdev.animemanga.data.remote.model.anime

import com.returdev.animemanga.data.remote.model.core.component.GenreDataComponentResponse
import com.returdev.animemanga.data.remote.model.core.component.ImageDataComponentResponse
import com.returdev.animemanga.data.remote.model.core.component.ReleasedDataComponentResponse
import com.returdev.animemanga.data.remote.model.core.component.TitleDataComponentResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Data class representing the extended response for an anime object.
 *
 * @property id The MyAnimeList unique identifier for the anime.
 * @property webpImages The images associated with the anime, including WebP format.
 * @property trailer Information about the anime's trailer.
 * @property titles List of titles for the anime in various languages.
 * @property type The type of anime (e.g., TV, Movie, OVA).
 * @property score The average score of the anime.
 * @property numberOfScorers The number of users who have scored the anime.
 * @property rank The rank of the anime based on its score.
 * @property synopsis The synopsis or summary of the anime.
 * @property genres List of genres associated with the anime.
 * @property demographics List of demographic categories for the anime.
 * @property source The source material of the anime (e.g., Manga, Light novel).
 * @property episodes The total number of episodes.
 * @property status The current status of the anime (e.g., Airing, Finished).
 * @property airing Indicates if the anime is currently airing.
 * @property aired Information about the airing dates of the anime.
 * @property season The season in which the anime was released (e.g., spring, summer).
 */
@Serializable
data class AnimeExtendedResponse(
    @SerialName("mal_id") val id : Int,
    @SerialName("images") val webpImages : ImageDataComponentResponse,
    @SerialName("trailer") val trailer : AnimeTrailerInfoResponse,
    @SerialName("titles") val titles : List<TitleDataComponentResponse>,
    @SerialName("type") val type : String?,
    @SerialName("score") val score : Float,
    @SerialName("scored_by") val numberOfScorers : Long,
    @SerialName("rank") val rank : Int,
    @SerialName("synopsis") val synopsis : String?,
    @SerialName("genres") val genres : List<GenreDataComponentResponse>,
    @SerialName("demographics") val demographics : List<GenreDataComponentResponse>,
    @SerialName("source") val source : String,
    @SerialName("episodes") val episodes : Int,
    @SerialName("status") val status : String,
    @SerialName("airing") val airing : Boolean,
    @SerialName("aired") val aired : ReleasedDataComponentResponse,
    @SerialName("season") val season : String?
)