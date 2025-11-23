package com.returdev.animemanga.ui.model.detailed

import com.returdev.animemanga.domain.model.core.GenreModel
import com.returdev.animemanga.domain.model.core.ReleasedModel
import com.returdev.animemanga.domain.model.core.TitleModel
import com.returdev.animemanga.ui.model.basic.VisualMediaBasicUi
import com.returdev.animemanga.ui.model.core.TitleUi

/**
 * Represents the detailed UI model for a manga item.
 *
 * This class extends [VisualMediaDetailedUi] and adds manga-specific fields such as
 * chapter count, volume count, publishing status, and publication dates.
 *
 * @property basicInfo Basic information about the manga (title, image, type, score, etc.).
 * @property extraTitles A list of alternative or translated titles for the manga.
 * @property numberOfScorers Total number of users who rated this manga.
 * @property rank The manga’s overall ranking among all listed works.
 * @property synopsis A descriptive summary of the manga’s story.
 * @property status The current status of the manga (e.g., "Finished", "Publishing").
 * @property demographics A list of demographic categories (e.g., Shounen, Seinen).
 * @property genres A list of genres associated with the manga (e.g., Action, Romance).
 * @property chapters Total number of chapters available for the manga.
 * @property volumes Total number of published volumes.
 * @property isPublishing Indicates whether the manga is currently being published.
 * @property published The release information, including start/end publication dates.
 */
data class MangaDetailedUi(
    override val basicInfo: VisualMediaBasicUi,
    override val extraTitles: List<TitleUi>,
    override val numberOfScorers: Long,
    override val rank: Int,
    override val synopsis: String,
    override val status: String,
    override val demographics: List<GenreModel>,
    override val genres: List<GenreModel>,
    val chapters : Int,
    val volumes : Int,
    val isPublishing : Boolean,
    val published : ReleasedModel
) : VisualMediaDetailedUi()
