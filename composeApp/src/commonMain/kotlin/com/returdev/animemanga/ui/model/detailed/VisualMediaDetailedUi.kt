package com.returdev.animemanga.ui.model.detailed

import com.returdev.animemanga.domain.model.core.GenreModel
import com.returdev.animemanga.ui.model.basic.VisualMediaBasicUi
import com.returdev.animemanga.ui.model.core.TitleUi

/**
 * Represents the full detailed UI model for a visual media item (anime or manga).
 *
 * This sealed class defines the common properties required to display detailed information
 * about any type of visual media. Concrete subclasses (e.g., AnimeDetailedUi, MangaDetailedUi)
 * will extend this class and provide additional fields specific to their media type.
 *
 * Each property here directly corresponds to data typically shown on a media detail screen.
 */
sealed class VisualMediaDetailedUi() {

    /**
     * Basic information about the visual media item, such as title, image, type, or rating.
     * This is usually the minimal set of info shown in lists or previews.
     */
    abstract val basicInfo: VisualMediaBasicUi

    /**
     * A list of alternative or translated titles for this media.
     * These may include English titles, Japanese titles, or other variants.
     */
    abstract val extraTitles: List<TitleUi>

    /**
     * The number of users who have contributed to the score or rating of this media.
     * Useful for evaluating score reliability.
     */
    abstract val numberOfScorers: Long

    /**
     * The ranking of this media compared to all other entries (e.g., #42 most popular).
     */
    abstract val rank: Int

    /**
     * A descriptive summary of the media's story or premise.
     */
    abstract val synopsis: String

    /**
     * The publishing or airing status (e.g., "Finished", "Airing", "Ongoing").
     */
    abstract val status: String

    /**
     * A list of demographic categories associated with the media
     */
    abstract val demographics: List<GenreModel>

    /**
     * A list of genres assigned to the media (e.g., Action, Romance, Fantasy).
     */
    abstract val genres: List<GenreModel>
}

