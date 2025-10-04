package com.returdev.animemanga.core.roompaging

/**
 * Represents the current state of a Room-based pager.
 *
 * @param T The type of data being paged.
 */
sealed class RoomPagerState<out T> {


    object Loading : RoomPagerState<Nothing>()
    data class Loaded<T>(val items: List<T>) : RoomPagerState<T>()

}
