package com.returdev.animemanga.data.paging

import androidx.paging.PagingSource
import com.returdev.animemanga.data.remote.model.core.extension.toDomainModel
import com.returdev.animemanga.data.remote.model.core.extension.toRemoteSearchFilters
import com.returdev.animemanga.data.remote.model.core.wrapper.data.PagedDataResponse
import com.returdev.animemanga.data.remote.model.core.wrapper.response.ApiResponse
import com.returdev.animemanga.data.remote.model.manga.MangaReducedResponse
import com.returdev.animemanga.data.remote.repository.MangaRemoteRepository
import com.returdev.animemanga.domain.model.core.result.DomainResult
import com.returdev.animemanga.domain.model.core.search.SearchFilters
import com.returdev.animemanga.domain.model.manga.MangaBasicModel
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Paging source provider for fetching paginated manga data from the remote repository.
 *
 * This class extends [VisualMediaPagingSource] to bridge API responses
 * ([MangaReducedResponse]) into domain models ([MangaBasicModel]).
 * It provides factory functions to create [PagingSource] instances
 * for manga search and top manga queries.
 *
 * @property mangaRemoteRepository The repository responsible for fetching manga data from the API.
 * @property dispatcher The [CoroutineDispatcher] used for executing API requests and data transformations.
 */
class MangaPagingSource(
    private val mangaRemoteRepository : MangaRemoteRepository,
    override val dispatcher : CoroutineDispatcher,
) : VisualMediaPagingSource<MangaReducedResponse, MangaBasicModel>() {

    /**
     * Creates a paging source for searching manga.
     *
     * @param query Optional manga title or partial title to search for.
     * @param filters Search filters such as type, status, or genres.
     * @param isAdultEntriesDisabled Whether to exclude adult (R-18) content.
     * @return A [PagingSource] emitting search results as [MangaBasicModel].
     */
    fun getMangaSearch(
        query : String?,
        filters : SearchFilters.MangaFilters,
        isAdultEntriesDisabled : Boolean
    ) : PagingSource<Int, MangaBasicModel> = createVisualMediaPagingSource { page ->
        mangaRemoteRepository.mangaSearch(
            page = page,
            title = query,
            filters = filters.toRemoteSearchFilters(),
            isAdultEntriesDisabled = isAdultEntriesDisabled,
        )
    }

    /**
     * Creates a paging source for fetching top manga.
     *
     * @param type Optional manga type filter (e.g., Manga, Light Novel).
     * @param isAdultEntriesDisabled Whether to exclude adult (R-18) content.
     * @param itemsLimit Optional maximum number of items to load.
     * @return A [PagingSource] emitting top [MangaBasicModel] items.
     */
    fun getTopManga(
        type : String? = null,
        isAdultEntriesDisabled : Boolean,
        itemsLimit : Int? = null
    ) : PagingSource<Int, MangaBasicModel> = createVisualMediaPagingSource(itemsLimit) { page ->
        mangaRemoteRepository.getTopManga(
            page = page,
            type = type,
            isAdultEntriesDisabled = isAdultEntriesDisabled
        )
    }

    /**
     * Maps API responses into domain results by converting
     * [MangaReducedResponse] DTOs into [MangaBasicModel] domain models.
     *
     * @param data The raw API response containing paginated manga data.
     * @return A [DomainResult] containing a list of [MangaBasicModel].
     */
    override fun mapResponseToDomain(
        data : ApiResponse<PagedDataResponse<MangaReducedResponse>>
    ) : DomainResult<List<MangaBasicModel>> {
        return data.toDomainModel<MangaReducedResponse, MangaBasicModel> { it.toDomainModel() }
    }
}
