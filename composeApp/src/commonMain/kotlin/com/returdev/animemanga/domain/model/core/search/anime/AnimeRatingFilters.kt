package com.returdev.animemanga.domain.model.core.search.anime

/**
 * Represents available rating filters for anime content.
 *
 * These values are typically based on age-appropriate classifications
 * and can be used to filter anime by audience suitability.
 */
enum class AnimeRatingFilters {

    /** Suitable for all ages (general audience). */
    G,

    /** Parental guidance suggested (may contain mild content). */
    PG,

    /** Parents strongly cautioned (may contain moderate content, suitable for ages 13+). */
    PG_13,

    /** Restricted (may contain mature content, suitable for ages 17+). */
    R_17,

    /** Mildly explicit content (not suitable for children, may include violence/nudity). */
    R_PLUS,

    /** Explicit adult content (18+ only). */
    RX
}
