package com.returdev.animemanga.ui.model.core

/**
 * Represents a title entry for a visual media item.
 *
 * This model is typically used when a media item contains multiple title variations
 * (e.g., English title, Japanese title, synonym titles).
 *
 * @property titleType The category or type of the title (e.g., "English", "Japanese", "Synonym").
 * @property title The actual title text associated with this type.
 */
data class TitleUi(
    val titleType: String,
    val title: String
)
