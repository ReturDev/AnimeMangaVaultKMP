package com.returdev.animemanga.domain.model.visualmedia

import com.returdev.animemanga.domain.model.core.GenreModel
import com.returdev.animemanga.domain.model.core.TitleModel


/**
 * Abstract class representing a visual media entity with common attributes.
 *
 * @property basicInfo Basic information about the visual media (e.g., id, images, title, type, score).
 * @property extraTitles Additional titles for the media (such as in different languages or synonyms).
 * @property numberOfScorers Number of users who have rated the media.
 * @property rank Ranking position of the media.
 * @property synopsis Brief summary or description of the media.
 * @property status Current status of the media (e.g., ongoing, completed).
 * @property demographics List of demographic genres associated with the media.
 * @property genres List of genres associated with the media.
 */
abstract class VisualMediaModel {
    abstract val basicInfo : VisualMediaBasicModel
    abstract val extraTitles : List<TitleModel>
    abstract val numberOfScorers: Long
    abstract val rank: Int
    abstract val synopsis: String
    abstract val status: String
    abstract val demographics : List<GenreModel>
    abstract val genres : List<GenreModel>
}