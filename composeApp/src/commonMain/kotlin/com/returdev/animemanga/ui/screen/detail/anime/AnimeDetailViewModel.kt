package com.returdev.animemanga.ui.screen.detail.anime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.returdev.animemanga.data.repository.AnimeRepository
import com.returdev.animemanga.data.repository.LibraryRepository
import com.returdev.animemanga.domain.model.anime.AnimeModel
import com.returdev.animemanga.domain.model.core.result.DomainResult
import com.returdev.animemanga.ui.model.detailed.AnimeDetailedUi
import com.returdev.animemanga.ui.model.extension.toDomain
import com.returdev.animemanga.ui.model.extension.toUI
import com.returdev.animemanga.ui.model.extension.toUi
import com.returdev.animemanga.ui.model.user.UserLibraryStatusUI
import com.returdev.animemanga.ui.screen.detail.DetailScreenState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for managing and exposing the detailed information
 * of a selected anime, as well as handling its library-related state.
 *
 * @property animeRepository Repository used to fetch anime details.
 * @property libraryRepository Repository used to read and modify the user's anime library status.
 * @property dispatcher Coroutine dispatcher used for asynchronous operations.
 */
class AnimeDetailViewModel(
    private val animeRepository : AnimeRepository,
    private val libraryRepository : LibraryRepository,
    private val dispatcher : CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailScreenState())
    val uiState = _uiState.asStateFlow()

    /**
     * Fetches detailed information about an anime by its [id].
     *
     * @param id Unique identifier of the anime.
     */
    fun getAnimeDetails(id : Int) {

        getAnimeLibraryStatus(id)

        viewModelScope.launch(dispatcher) {

            val result = animeRepository.getAnimeById(id)

            _uiState.update { state ->
                state.copy(
                    visualMediaState = when (result) {
                        is DomainResult.Error -> DetailScreenState.VisualMediaState.Error(result.errorType.name)
                        is DomainResult.PagedSuccess<*> -> throw IllegalArgumentException("Use Success for no paginated data")
                        is DomainResult.Success<*> -> DetailScreenState.VisualMediaState.Loaded(
                            (result.data as AnimeModel).toUi()
                        )
                    }
                )
            }
        }

    }


    /**
     * Updates the user's library status for a specific anime.
     *
     * @param animeId The ID of the anime whose status should be updated.
     * @param newStatus The new desired [UserLibraryStatusUI], or `null` to remove the status.
     */
    fun updateAnimeLibraryStatus(animeId : Int, newStatus : UserLibraryStatusUI?) {
        viewModelScope.launch {
            if (uiState.value.visualMediaLibraryStatus == null && newStatus != null) {

                val basicAnime =
                    uiState.value.visualMediaState.asLoaded<AnimeDetailedUi>() !!.basicInfo
                libraryRepository.saveAnimeWithStatus(
                    anime = basicAnime.toDomain(),
                    status = newStatus.toDomain()
                )

            } else {

                libraryRepository.changeAnimeStatus(animeId, newStatus?.toDomain())

            }
        }
    }


    /**
     * Observes the user's library status for the anime with the given [id].
     *
     * @param id The ID of the anime whose library status should be observed.
     */
    private fun getAnimeLibraryStatus(id : Int) {

        viewModelScope.launch {

            libraryRepository.getAnimeStatusById(id).collect {
                _uiState.update { state ->
                    state.copy(
                        visualMediaLibraryStatus = it?.toUI()
                    )
                }
            }

        }

    }

}