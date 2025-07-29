package com.returdev.animemanga.data.remote.model.core.wrapper.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Generic data class representing a paginated API response.
 *
 * @param T The type of the items contained in the response.
 * @property data The object containing the list of data items returned by the API.
 * @property pagination Information about the pagination of the response.
 */
@Serializable
data class PagedDataResponse<T>(
    @SerialName("data") val data : DataResponse<List<T>>,
    @SerialName("pagination") val pagination : PaginationResponse
) {

    /**
     * Data class representing pagination information in the response.
     *
     * @property lastVisiblePage The number of the last visible page.
     * @property hasNextPage Indicates if there is a next page.
     * @property itemsOnPageInfo Information about the items on the current page.
     */
    @Serializable
    data class PaginationResponse(
        @SerialName("last_visible_page") val lastVisiblePage : Int,
        @SerialName("has_next_page") val hasNextPage : Boolean,
        @SerialName("items") val itemsOnPageInfo : PageInfo
    ) {

        /**
         * Data class containing information about the total number of items.
         *
         * @property total The total number of available items.
         */
        @Serializable
        data class PageInfo(
            @SerialName("total") val total : Int
        )

    }
}