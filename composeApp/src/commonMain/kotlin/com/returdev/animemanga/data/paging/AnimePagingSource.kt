package com.returdev.animemanga.data.paging

import androidx.paging.PagingSource
import com.returdev.animemanga.data.remote.model.anime.AnimeReducedResponse
import com.returdev.animemanga.data.remote.model.core.extension.toDomainModel
import com.returdev.animemanga.data.remote.model.core.extension.toRemoteSearchFilters
import com.returdev.animemanga.data.remote.model.core.wrapper.data.PagedDataResponse
import com.returdev.animemanga.data.remote.model.core.wrapper.response.ApiResponse
import com.returdev.animemanga.data.remote.repository.AnimeRemoteRepository
import com.returdev.animemanga.domain.model.anime.AnimeBasicModel
import com.returdev.animemanga.domain.model.core.result.DomainResult
import com.returdev.animemanga.domain.model.core.search.SearchFilters
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Paging source provider for fetching paginated anime data from the remote repository.
 *
 * This class extends [VisualMediaPagingSource] and connects the generic paging
 * mechanism with [AnimeRemoteRepository]. It exposes factory functions to create
 * [PagingSource] instances for different types of anime queries such as search,
 * seasonal listings, and top anime.
 *
 * @property animeRemoteRepository The repository used to fetch anime data from the API.
 * @property dispatcher The [CoroutineDispatcher] on which API calls and transformations run.
 */
class AnimePagingSource(
    private val animeRemoteRepository : AnimeRemoteRepository,
    override val dispatcher : CoroutineDispatcher,
) : VisualMediaPagingSource<AnimeReducedResponse, AnimeBasicModel>() {

    /**
     * Creates a paging source for searching anime.
     *
     * @param query An optional title or partial title to search for.
     * @param filters Search filters such as type, status, or genres.
     * @param isAdultEntriesDisabled Whether to exclude adult (R-18) content.
     * @return A [PagingSource] emitting [AnimeBasicModel] search results.
     */
    fun getAnimeSearch(
        query : String?,
        filters : SearchFilters.AnimeFilters,
        isAdultEntriesDisabled : Boolean
    ) : PagingSource<Int, AnimeBasicModel> {
        return createVisualMediaPagingSource { page ->
            animeRemoteRepository.animeSearch(
                page = page,
                title = query,
                filters = filters.toRemoteSearchFilters(),
                isAdultEntriesDisabled = isAdultEntriesDisabled,
            )
        }
    }

    /**
     * Creates a paging source for fetching top anime.
     *
     * @param type Optional anime type filter (e.g., TV, Movie).
     * @param isAdultEntriesDisabled Whether to exclude adult (R-18) content.
     * @param itemsLimit Optional maximum number of items to load.
     * @return A [PagingSource] emitting top [AnimeBasicModel] items.
     */
    fun getTopAnime(
        type : String?,
        isAdultEntriesDisabled : Boolean,
        itemsLimit : Int? = null
    ) : PagingSource<Int, AnimeBasicModel> {
        return createVisualMediaPagingSource(itemsLimit) { page ->
            animeRemoteRepository.getTopAnime(
                page = page,
                type = type,
                isAdultEntriesDisabled = isAdultEntriesDisabled
            )
        }
    }

    /**
     * Creates a paging source for fetching the current season's anime.
     *
     * @param isAdultEntriesDisabled Whether to exclude adult (R-18) content.
     * @param type Optional anime type filter (e.g., TV, Movie).
     * @param itemsLimit Optional maximum number of items to load.
     * @return A [PagingSource] emitting [AnimeBasicModel] items from the current season.
     */
    fun getAnimeCurrentSeason(
        isAdultEntriesDisabled : Boolean,
        type : String?,
        itemsLimit : Int? = null,
    ) : PagingSource<Int, AnimeBasicModel> {
        return createVisualMediaPagingSource(itemsLimit) { page ->
            animeRemoteRepository.getAnimeCurrentSeason(
                page = page,
                isAdultEntriesDisabled = isAdultEntriesDisabled,
                type = type
            )
        }
    }

    /**
     * Creates a paging source for fetching anime from a specific season.
     *
     * @param year The release year of the season.
     * @param season The season name (e.g., "winter", "spring", "summer", "fall").
     * @param isAdultEntriesDisabled Whether to exclude adult (R-18) content.
     * @param type Optional anime type filter (e.g., TV, Movie).
     * @return A [PagingSource] emitting [AnimeBasicModel] items.
     */
    fun getAnimeSeason(
        year : Int,
        season : String,
        isAdultEntriesDisabled : Boolean,
        type : String?,
    ) : PagingSource<Int, AnimeBasicModel> {
        return createVisualMediaPagingSource { page ->
            animeRemoteRepository.getAnimeSeason(
                page = page,
                year = year,
                season = season,
                isAdultEntriesDisabled = isAdultEntriesDisabled,
                type = type
            )
        }
    }

    /**
     * Creates a paging source for fetching upcoming seasonal anime.
     *
     * @param isAdultEntriesDisabled Whether to exclude adult (R-18) content.
     * @param type Optional anime type filter (e.g., TV, Movie).
     * @return A [PagingSource] emitting [AnimeBasicModel] items from upcoming seasons.
     */
    fun getAnimeSeasonUpcoming(
        isAdultEntriesDisabled : Boolean,
        type : String? = null,
    ) : PagingSource<Int, AnimeBasicModel> {
        return createVisualMediaPagingSource { page ->
            animeRemoteRepository.getAnimeSeasonUpcoming(
                page = page,
                isAdultEntriesDisabled = isAdultEntriesDisabled,
                type = type
            )
        }
    }

    /**
     * Maps API responses into domain results by converting
     * [AnimeReducedResponse] DTOs into [AnimeBasicModel] domain models.
     *
     * @param data The raw API response containing paginated anime data.
     * @return A [DomainResult] containing a list of [AnimeBasicModel].
     */
    override fun mapResponseToDomain(
        data : ApiResponse<PagedDataResponse<AnimeReducedResponse>>
    ) : DomainResult<List<AnimeBasicModel>> {
        return data.toDomainModel<AnimeReducedResponse, AnimeBasicModel> { it.toDomainModel() }
    }
}
