package com.returdev.animemanga.domain.model.visualmedia

import com.returdev.animemanga.domain.model.core.TitleModel

/**
 * Abstract class representing a visual media item with its main attributes.
 *
 * @property basicInfo Basic information about the visual media (such as id, images, title, type, score).
 * @property extraTitles Additional titles for the media (e.g., in different languages or synonyms).
 * @property numberOfScorers The number of users who have scored/rated the media.
 * @property rank The ranking position of the media.
 * @property synopsis A brief summary or description of the media.
 * @property status The current status of the media (e.g., ongoing, completed).
 * @property genreIds List of genre identifiers associated with the media.
 * @property demographicIds List of demographic identifiers associated with the media.
 */
abstract class VisualMediaModel {
    abstract val basicInfo : VisualMediaBasicModel
    abstract val extraTitles : List<TitleModel>
    abstract val numberOfScorers: Long
    abstract val rank: Int
    abstract val synopsis: String
    abstract val status: String
    abstract val genreIds: List<Int>
    abstract val demographicIds: List<Int>
}