package com.returdev.animemanga.ui.screen.detail.manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.returdev.animemanga.data.repository.LibraryRepository
import com.returdev.animemanga.data.repository.MangaRepository
import com.returdev.animemanga.domain.model.core.result.DomainResult
import com.returdev.animemanga.domain.model.manga.MangaModel
import com.returdev.animemanga.ui.model.detailed.MangaDetailedUi
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
 * ViewModel responsible for managing and providing detailed manga information
 * as well as the user's library status for the selected manga.
 *
 * @property mangaRepository Repository used to fetch detailed manga information.
 * @property libraryRepository Repository used to manage the user's library entries.
 * @property dispatcher The coroutine dispatcher used for running repository operations.
 */
class MangaDetailViewModel(
    private val mangaRepository : MangaRepository,
    private val libraryRepository : LibraryRepository,
    private val dispatcher : CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailScreenState())
    val uiState = _uiState.asStateFlow()

    /**
     * Retrieves the detailed information for a manga with the given [id].
     *
     * @param id The unique identifier of the manga to fetch.
     */
    fun getMangaDetails(id : Int) {

        getMangaLibraryStatus(id)

        viewModelScope.launch(dispatcher) {

            val result = mangaRepository.getMangaById(id)

            _uiState.update { state ->
                state.copy(
                    visualMediaState = when (result) {
                        is DomainResult.Error -> DetailScreenState.VisualMediaState.Error(result.errorType.name)
                        is DomainResult.PagedSuccess<*> -> throw IllegalArgumentException("Use Success for no paginated data")
                        is DomainResult.Success<*> -> DetailScreenState.VisualMediaState.Loaded(
                            (result.data as MangaModel).toUi()
                        )
                    }
                )
            }
        }

    }

    /**
     * Updates the user's library status for the manga with the given [mangaId].
     *
     * @param mangaId The ID of the manga whose library status is being changed.
     * @param newStatus The new status selected by the user, or `null` to remove the entry.
     */
    fun updateMangaLibraryStatus(mangaId : Int, newStatus : UserLibraryStatusUI?) {
        viewModelScope.launch {

            if (uiState.value.visualMediaLibraryStatus == null && newStatus != null) {

                val basicManga = uiState.value.visualMediaState.asLoaded<MangaDetailedUi>() !!.basicInfo
                libraryRepository.saveMangaWithStatus(
                    manga = basicManga.toDomain(),
                    status = newStatus.toDomain()
                )

            } else {

                libraryRepository.changeMangaStatus(mangaId, newStatus = newStatus?.toDomain())

            }

        }

    }

    /**
     * Begins observing the library status for a manga with the given [id].
     *
     * @param id The ID of the manga whose library status should be observed.
     */
    private fun getMangaLibraryStatus(id : Int) {

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