package com.majestadev.data.repository.paging.locations

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.majestadev.data.cache.Cache
import com.majestadev.data.network.Network
import com.majestadev.domain.dto.LocationFilter
import com.majestadev.domain.entity.network.locations.LocationDetails

class FilteredLocationsPagingSource(
    private val locationFilter: LocationFilter,
    private val cache: Cache,
    private val network: Network
) : PagingSource<Int, LocationDetails>() {

    override fun getRefreshKey(state: PagingState<Int, LocationDetails>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocationDetails> {
        var results: List<LocationDetails>

        val page: Int = params.key ?: FIRST_PAGE_INDEX
        val pageSize = params.loadSize
        var nextKey: Int? = null

        try {
            network.getFilteredLocations(locationFilter, page).apply {

                if (this.info.next != null) {
                    val uri = Uri.parse(this.info.next)
                    val nextPageQuery = uri.getQueryParameter("page")
                    nextKey = nextPageQuery?.toInt()
                }

                results = this.results

                results.forEach { locationDetails ->
                    cache.addLocation(locationDetails.toLocationEntity())
                }
            }
        } catch (e: Exception) {
            cache.getFilteredLocations(locationFilter).apply {
                nextKey = if (size < pageSize) null else nextKey?.plus(1)
                results = this.map { locationEntity ->
                    LocationDetails(
                        locationEntity.id,
                        locationEntity.name,
                        locationEntity.type,
                        locationEntity.dimension,
                        emptyList()
                    )
                }
            }
        }

        return LoadResult.Page(data = results, prevKey = null, nextKey = nextKey)
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }
}