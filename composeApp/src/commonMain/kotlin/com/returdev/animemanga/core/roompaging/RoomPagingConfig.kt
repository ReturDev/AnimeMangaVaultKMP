package com.returdev.animemanga.core.roompaging

/**
 * Configuration class for controlling paging behavior when using [RoomPagingSource].
 *
 * @param pageSize The number of items to load per page.
 * @param maxPages The maximum number of pages to keep in memory at once.
 * 
 */
data class RoomPagingConfig(
    val pageSize: Int,
    val maxPages: Int,
)
