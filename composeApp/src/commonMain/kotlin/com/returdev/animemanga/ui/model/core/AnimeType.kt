package com.returdev.animemanga.ui.model.core

enum class AnimeType() : VisualMediaType {
    TV() {
        override val typeName : String = "TV"
        override val scrimTypeName : String = "TV"
    },
    MOVIE() {
        override val typeName : String = "Movie"
        override val scrimTypeName : String = "Movie"
    },
    OVA() {
        override val typeName : String = "OVA"
        override val scrimTypeName : String = "OVA"
    },
    SPECIAL() {
        override val typeName : String = "Special"
        override val scrimTypeName : String = "Special"
    },
    ONA() {
        override val typeName : String = "ONA"
        override val scrimTypeName : String = "ONA"
    },
    MUSIC() {
        override val typeName : String = "Music"
        override val scrimTypeName : String = "Music"
    },
    PV() {
        override val typeName : String = "Promotional Video"
        override val scrimTypeName : String = "PV"
    },
    CM() {
        override val typeName : String = "Commercial"
        override val scrimTypeName : String = "CM"
    },
    TV_SPECIAL() {
        override val typeName : String = "TV Special"
        override val scrimTypeName : String = "TVS"
    },
    UNKNOWN() {
        override val typeName : String = "Unknown"
        override val scrimTypeName : String = "?"
    };

    companion object {
        fun fromString(value : String?) : AnimeType {
            return value?.replace(Regex("[^A-Za-z]"), "_").let { formatedValue ->
                entries.firstOrNull { it.name.equals(formatedValue, ignoreCase = true) }
            } ?: UNKNOWN
        }
    }
}