package com.returdev.animemanga.domain.model.manga

import com.returdev.animemanga.domain.model.core.GenreModel
import com.returdev.animemanga.domain.model.core.ReleasedModel
import com.returdev.animemanga.domain.model.core.TitleModel
import com.returdev.animemanga.domain.model.visualmedia.VisualMediaBasicModel
import com.returdev.animemanga.domain.model.visualmedia.VisualMediaModel

/**
 * Represents a detailed domain model for a manga.
 *
 * Extends [VisualMediaModel] to include common visual media properties
 *
 * @property basicInfo Basic information about the manga (title, image, etc.).
 * @property extraTitles Additional titles for the manga in different languages or variations.
 * @property numberOfScorers Total number of users who have scored this manga.
 * @property rank Ranking of the manga based on scores or popularity.
 * @property synopsis A brief description or summary of the manga's story.
 * @property status Current publishing status (e.g., ongoing, completed).
 * @property demographics Target audience genres or demographic categories (e.g., Shounen, Seinen).
 * @property genres List of general genres associated with the manga (e.g., Action, Romance).
 * @property chapters Total number of chapters published.
 * @property volumes Total number of volumes published.
 * @property isPublishing Indicates whether the manga is currently being published.
 * @property published Release information containing start and end dates.
 */
data class MangaModel(
    override val basicInfo: VisualMediaBasicModel,
    override val extraTitles: List<TitleModel>,
    override val numberOfScorers: Long,
    override val rank: Int,
    override val synopsis: String,
    override val status: String,
    override val demographics: List<GenreModel>,
    override val genres: List<GenreModel>,
    val chapters: Int,
    val volumes: Int,
    val isPublishing: Boolean,
    val published: ReleasedModel
) : VisualMediaModel()
