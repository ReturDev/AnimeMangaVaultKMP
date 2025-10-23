package com.returdev.animemanga.ui.screen.library

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.returdev.animemanga.core.Logger
import com.returdev.animemanga.core.roompaging.RoomPager
import com.returdev.animemanga.core.roompaging.RoomPagerState
import com.returdev.animemanga.data.library.model.LibraryOrderBy
import com.returdev.animemanga.data.repository.LibraryRepository
import com.returdev.animemanga.domain.model.anime.AnimeBasicModel
import com.returdev.animemanga.domain.model.core.search.common.SortDirection
import com.returdev.animemanga.domain.model.manga.MangaBasicModel
import com.returdev.animemanga.ui.model.basic.VisualMediaBasicUi
import com.returdev.animemanga.ui.model.extension.toDomain
import com.returdev.animemanga.ui.model.extension.toUi
import com.returdev.animemanga.ui.model.user.UserLibraryStatusUI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for managing and providing the state of the Library screen.
 *
 * @param libraryRepository Repository for accessing library-related data.
 * @param dispatcher Coroutine dispatcher used for running async operations.
 */
class LibraryViewModel(
    private val libraryRepository : LibraryRepository,
    private val dispatcher : CoroutineDispatcher
) : ViewModel() {

    /** Holds the current UI state for the screen. */
    private val _uiState = MutableStateFlow(createState())
    val uiState = _uiState.asStateFlow()

    /**
     * Fetches anime items by their library status (e.g., Watching, Completed).
     * Initializes pagination only if it hasn't been initialized yet for this status.
     *
     * @param status The selected [UserLibraryStatusUI] for anime.
     */
    fun getAnimesByStatus(status : UserLibraryStatusUI) {

        _uiState.value.animeStatusData[status]!!.let { libraryData ->
            if (libraryData.status == LibraryScreenState.DataStatus.INITIALIZED) return

            val pager = libraryRepository.getAnimesByStatusPager(
                status = status.toDomain(),
                orderBy = LibraryOrderBy.ADDED_DATE,
                sortDirection = SortDirection.ASCENDANT
            )

            initializePagingFlowByStatus(
                status = status,
                lazyGridState = libraryData.scrollState,
                pager = pager,
                update = ::updateAnimeState
            )
        }
    }

    /**
     * Fetches manga items by their library status.
     * Initializes pagination if not done before for this status.
     *
     * @param status The selected [UserLibraryStatusUI] for manga.
     */
    fun getMangasByStatus(status : UserLibraryStatusUI) {

        _uiState.value.mangaStatusData[status]!!.let { libraryData ->
            if (libraryData.status == LibraryScreenState.DataStatus.INITIALIZED) return

            val pager = libraryRepository.getMangasByStatus(
                status = status.toDomain(),
                orderBy = LibraryOrderBy.ADDED_DATE,
                sortDirection = SortDirection.ASCENDANT
            )

            initializePagingFlowByStatus(
                status = status,
                lazyGridState = libraryData.scrollState,
                pager = pager,
                update = ::updateMangaState
            )
        }
    }

    /**
     * Initializes the paging flow for a specific media type (Anime or Manga).
     *
     * @param status The current user-selected media status.
     * @param lazyGridState Scroll state used for LazyVerticalGrid.
     * @param pager Pager that handles pagination logic and data collection.
     * @param update Function that updates the corresponding UI state (Anime/Manga).
     */
    private fun <T, R> initializePagingFlowByStatus(
        status : UserLibraryStatusUI,
        lazyGridState : LazyGridState,
        pager : RoomPager<T, R>,
        update : (UserLibraryStatusUI, RoomPagerState<R>) -> Unit
    ) {
        viewModelScope.launch(dispatcher) {
            pager.initialize(lazyGridState)

            pager.items.collect { data ->
                update(status, data)
            }

        }.invokeOnCompletion {
            Logger.i("LibraryViewModel", "Pager invalidated")
            pager.invalidate()
        }
    }

    /** Updates the UI state with anime data for the given status. */
    private fun updateAnimeState(
        status : UserLibraryStatusUI,
        roomPagerState : RoomPagerState<AnimeBasicModel>
    ) {
        _uiState.update { state ->
            state.copy(
                animeStatusData = updateCategoryState(
                    status = status,
                    roomPagerState = roomPagerState,
                    currentMap = state.animeStatusData,
                    mapper = AnimeBasicModel::toUi
                )
            )
        }
    }

    /** Updates the UI state with manga data for the given status. */
    private fun updateMangaState(
        status : UserLibraryStatusUI,
        roomPagerState : RoomPagerState<MangaBasicModel>
    ) {
        _uiState.update { state ->
            state.copy(
                mangaStatusData = updateCategoryState(
                    status = status,
                    roomPagerState = roomPagerState,
                    currentMap = state.mangaStatusData,
                    mapper = MangaBasicModel::toUi
                )
            )
        }
    }

    /**
     * Maps raw data to UI model and updates the state of Anime or Manga.
     *
     * @param status Library status to update (Watching, Completed, etc.).
     * @param roomPagerState The latest pagination state.
     * @param currentMap Current data map to modify.
     * @param mapper Function to convert domain data to UI model.
     */
    private fun <T, R : VisualMediaBasicUi> updateCategoryState(
        status : UserLibraryStatusUI,
        roomPagerState : RoomPagerState<T>,
        currentMap : Map<UserLibraryStatusUI, LibraryScreenState.LibraryScreenData>,
        mapper : (T) -> R
    ) : Map<UserLibraryStatusUI, LibraryScreenState.LibraryScreenData> {
        val mutableMap = currentMap.toMutableMap()

        mutableMap[status]!!.let { libraryData ->
            val mappedState = when (roomPagerState) {
                is RoomPagerState.Loaded -> RoomPagerState.Loaded(roomPagerState.items.map(mapper))
                RoomPagerState.Loading -> RoomPagerState.Loading
            }

            mutableMap[status] = libraryData.copy(
                status = LibraryScreenState.DataStatus.INITIALIZED,
                pagerState = mappedState
            )
        }

        return mutableMap
    }

    /**
     * Initializes UI state for Anime and Manga with each possible status.
     */
    private fun createState() : LibraryScreenState {
        val mangaStatusData =
            mutableMapOf<UserLibraryStatusUI, LibraryScreenState.LibraryScreenData>()
        val animeStatusData =
            mutableMapOf<UserLibraryStatusUI, LibraryScreenState.LibraryScreenData>()

        UserLibraryStatusUI.entries.forEach { statusUI ->
            mangaStatusData[statusUI] = LibraryScreenState.LibraryScreenData()
            animeStatusData[statusUI] = LibraryScreenState.LibraryScreenData()
        }

        return LibraryScreenState(
            animeStatusData = animeStatusData,
            mangaStatusData = mangaStatusData
        )
    }
}