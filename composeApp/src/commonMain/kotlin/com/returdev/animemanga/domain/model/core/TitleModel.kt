package com.returdev.animemanga.domain.model.core

/**
 * Sealed class representing different types of titles for a media item.
 *
 * @property title The actual title string.
 */
sealed class TitleModel {


    abstract val title: String

    class DefaultTitle (override val title: String) : TitleModel()

    class JapaneseTitle (override val title: String) : TitleModel()

    class EnglishTitle (override val title: String) : TitleModel()

    class SynonymTitle (override val title: String) : TitleModel()

    companion object {
        /**
         * Returns a specific TitleModel instance based on the provided title key.
         *
         * @param titleKey The key indicating the type of title (e.g., "default", "japanese").
         * @param title The actual title string.
         * @return The corresponding TitleModel instance, or null if the key is invalid.
         */
        fun getTitle(titleKey: String, title: String): TitleModel? {
            return when (TitleKey.fromString(titleKey)) {
                TitleKey.DEFAULT -> DefaultTitle(title)
                TitleKey.JAPANESE -> JapaneseTitle(title)
                TitleKey.ENGLISH -> EnglishTitle(title)
                TitleKey.SYNONYM -> SynonymTitle(title)
                null -> null
            }
        }
    }

    /**
     * Enum representing the possible types of title keys.
     */
    private enum class TitleKey {
        DEFAULT, JAPANESE, ENGLISH, SYNONYM;

        companion object {
            /**
             * Converts a string to a TitleKey enum value, ignoring case.
             *
             * @param title The string to convert.
             * @return The corresponding TitleKey, or null if the string does not match any key.
             */
            fun fromString(title: String): TitleKey? =
                runCatching { valueOf(title.uppercase()) }.getOrNull()
        }
    }
}