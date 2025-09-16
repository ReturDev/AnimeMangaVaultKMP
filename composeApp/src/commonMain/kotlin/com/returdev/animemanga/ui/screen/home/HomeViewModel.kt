package com.returdev.animemanga.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.returdev.animemanga.data.repository.AnimeRepository
import com.returdev.animemanga.data.repository.MangaRepository
import com.returdev.animemanga.ui.model.extension.toUi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for the Home screen.
 *
 * Responsible for fetching and managing UI state related to top anime,
 * current season anime, and top manga. Uses repositories to load data
 * and exposes state via [StateFlow] to be observed by the UI.
 *
 * @property animeRepository Repository for accessing anime data.
 * @property mangaRepository Repository for accessing manga data.
 * @property dispatcher Coroutine dispatcher for background work (e.g., IO).
 */
class HomeViewModel(
    private val animeRepository: AnimeRepository,
    private val mangaRepository: MangaRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeScreenState> = MutableStateFlow(HomeScreenState())

    val uiState = _uiState.asStateFlow()

    companion object {
        private const val ITEMS_LIMIT = 15
    }

    init {
        // Fetch all main sections as soon as the ViewModel is created.
        getTopAnime()
        getAnimeCurrentSeason()
        getTopManga()
    }

    /**
     * Fetches the top anime list from the repository.
     *
     * The data is:
     * - Retrieved with a fixed item limit.
     * - Mapped from domain models to UI models (`toUi()`).
     * - Cached in [viewModelScope] to preserve results during recompositions.
     * - Stored in the UI state flow.
     */
    fun getTopAnime() {
        viewModelScope.launch(dispatcher) {
            val topAnime = animeRepository.getTopAnime(
                type = null,
                itemsLimit = ITEMS_LIMIT
            )
                .map { page -> page.map { it.toUi() } }
                .cachedIn(viewModelScope)

            _uiState.update { state ->
                state.copy(topAnime = topAnime)
            }
        }
    }

    /**
     * Fetches the current season anime list from the repository.
     *
     * Similar to [getTopAnime], but requests data for the ongoing season.
     * Results are mapped, cached, and stored in the UI state.
     */
    fun getAnimeCurrentSeason() {
        viewModelScope.launch(dispatcher) {
            val currentSeason = animeRepository.getAnimeCurrentSeason(
                type = null,
                itemsLimit = ITEMS_LIMIT
            )
                .map { page -> page.map { it.toUi() } }
                .cachedIn(viewModelScope)

            _uiState.update { state ->
                state.copy(animeCurrentSeason = currentSeason)
            }
        }
    }

    /**
     * Fetches the top manga list from the repository.
     *
     * Works identically to [getTopAnime], but for manga data.
     * Results are transformed to UI models, cached, and stored in the UI state.
     */
    fun getTopManga() {
        viewModelScope.launch(dispatcher) {
            val topManga = mangaRepository.getTopManga(
                type = null,
                itemsLimit = ITEMS_LIMIT
            )
                .map { page -> page.map { it.toUi() } }
                .cachedIn(viewModelScope)

            _uiState.update { state ->
                state.copy(topManga = topManga)
            }
        }
    }
}
