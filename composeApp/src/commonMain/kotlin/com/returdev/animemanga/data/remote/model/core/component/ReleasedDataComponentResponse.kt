package com.returdev.animemanga.data.remote.model.core.component

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class representing the release date component in the API response.
 *
 * @property date An object containing the start and end dates of the release period.
 */
@Serializable
data class ReleasedDataComponentResponse(
    @SerialName("prop") val date : ReleasedDate
) {

    /**
     * Data class representing the release period with start and optional end dates.
     *
     * @property startDate The start date of the release period.
     * @property endDate The end date of the release period (nullable).
     */
    @Serializable
    data class ReleasedDate(
        @SerialName("from") val startDate: DateResponse,
        @SerialName("to") val endDate: DateResponse?
    ){
        /**
         * Data class representing a specific date.
         *
         * @property day The day of the date.
         * @property month The month of the date.
         * @property year The year of the date.
         */
        @Serializable
        data class DateResponse(
            @SerialName("day") val day: Int,
            @SerialName("month") val month: Int,
            @SerialName("year")val year: Int
        )
    }

}