package com.returdev.animemanga.ui.screen.showmore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.returdev.animemanga.data.repository.AnimeRepository
import com.returdev.animemanga.data.repository.MangaRepository
import com.returdev.animemanga.ui.model.basic.VisualMediaBasicUi
import com.returdev.animemanga.ui.model.extension.toUi
import com.returdev.animemanga.ui.screen.showmore.model.ShowMoreSection
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for loading paginated lists of visual media for the "Show More" screen.
 *
 * @property animeRepository Repository used for loading paginated anime data.
 * @property mangaRepository Repository used for loading paginated manga data.
 * @property dispatcher Coroutine dispatcher used for running loading operations.
 */
class ShowMoreViewModel(
    private val animeRepository : AnimeRepository,
    private val mangaRepository : MangaRepository,
    private val dispatcher : CoroutineDispatcher
) : ViewModel() {

    /**
     * Internal mutable state of paginated UI models.
     *
     * Exposed as [uiState] so UI can collect updates.
     */
    private val _uiState = MutableStateFlow<PagingData<VisualMediaBasicUi>>(PagingData.empty())

    /**
     * The state flow representing the current paginated UI data.
     *
     * Observed by the composable responsible for displaying lists of visual media.
     */
    val uiState = _uiState.asStateFlow()

    /**
     * Initializes data loading for the specified [section].
     *
     * The loaded domain models are mapped into UI models using their `.toUi()` extension.
     *
     * @param section The section defining what type of paginated content to load.
     */
    fun init(section : ShowMoreSection) {

        when (section) {
            ShowMoreSection.TOP_ANIME -> initState {
                animeRepository.getTopAnime(null).map { page -> page.map { it.toUi() } }
            }

            ShowMoreSection.TOP_MANGA -> initState {
                mangaRepository.getTopManga(null).map { page -> page.map { it.toUi() } }
            }

            ShowMoreSection.CURRENT_SEASON -> initState {
                animeRepository.getAnimeCurrentSeason(null).map { page -> page.map { it.toUi() } }
            }
        }
    }

    /**
     * Initializes the paging state using the provided [getData] function.
     *
     * @param getData A suspend function returning a [Flow] of paginated UI models.
     */
    private fun <T : VisualMediaBasicUi> initState(getData : suspend () -> Flow<PagingData<T>>) {
        viewModelScope.launch(dispatcher) {
            getData()
                .cachedIn(viewModelScope)
                .collect { data ->
                    _uiState.value = data.map { it as VisualMediaBasicUi }
                }
        }
    }
}
