package com.returdev.animemanga.ui.model.core

enum class MangaType() : VisualMediaType {
    MANGA() {
        override val typeName : String = "Manga"
        override val scrimTypeName: String = "Manga"
    },
    NOVEL() {
        override val typeName : String = "Novel"
        override val scrimTypeName: String = "Novel"
    },
    LIGHT_NOVEL() {
        override val typeName : String = "Light Novel"
        override val scrimTypeName: String = "L.Novel"

    },
    ONE_SHOT() {
        override val typeName : String = "One-Shot"
        override val scrimTypeName: String = "One-Shot"
    },
    DOUJIN() {
        override val typeName : String = "Doujin"
        override val scrimTypeName: String = "Doujin"
    },
    MANHWA() {
        override val typeName : String = "Manhwa"
        override val scrimTypeName: String = "Manhwa"
    },
    MANHUA() {
        override val typeName : String = "Manhua"
        override val scrimTypeName: String = "Manhua"
    },
    UNKNOWN() {
        override val typeName : String = "Unknown"
        override val scrimTypeName: String = "?"
    };

    companion object {
        fun fromString(value : String?) : MangaType {
            return value?.replace(Regex("[^A-Za-z]"), "_").let { formatedValue ->
                MangaType.entries.firstOrNull { it.name.equals(formatedValue, ignoreCase = true) }
            } ?: MangaType.UNKNOWN
        }
    }
}