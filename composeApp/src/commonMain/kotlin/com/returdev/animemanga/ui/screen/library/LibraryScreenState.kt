package com.returdev.animemanga.ui.screen.library

import androidx.compose.foundation.lazy.grid.LazyGridState
import com.returdev.animemanga.core.roompaging.RoomPagerState
import com.returdev.animemanga.ui.model.basic.AnimeBasicUi
import com.returdev.animemanga.ui.model.basic.MangaBasicUi
import com.returdev.animemanga.ui.model.basic.VisualMediaBasicUi
import com.returdev.animemanga.ui.model.user.UserLibraryStatusUI


/**
 * Represents the UI state of the Library Screen.
 *
 *
 * @param animeStatusData A map of [UserLibraryStatusUI] to [LibraryScreenData] representing the state of Anime data.
 * @param mangaStatusData A map of [UserLibraryStatusUI] to [LibraryScreenData] representing the state of Manga data.
 */
data class LibraryScreenState(
    val animeStatusData: Map<UserLibraryStatusUI, LibraryScreenData>,
    val mangaStatusData: Map<UserLibraryStatusUI, LibraryScreenData>
) {

    /**
     * Holds UI-related data for a specific status (e.g., Watching, Completed)
     * within either Anime or Manga sections of the library.
     *
     * @param status Represents whether data for this status has been initialized or is idle.
     * @param scrollState Stores the scroll position for the LazyVerticalGrid to maintain UI state during recompositions.
     * @param pagerState Represents the current paging state – loading, loaded, or error – along with the paginated items.
     */
    data class LibraryScreenData(
        val status: DataStatus = DataStatus.IDLE,
        val scrollState: LazyGridState = LazyGridState(),
        val pagerState: RoomPagerState<VisualMediaBasicUi> = RoomPagerState.Loading
    )

    /**
     * Represents the initialization status of the data for a specific library category and status.
     *
     * - [IDLE]: No data has been loaded yet.
     * - [INITIALIZED]: Data has been loaded at least once.
     */
    enum class DataStatus {
        IDLE,
        INITIALIZED
    }
}
