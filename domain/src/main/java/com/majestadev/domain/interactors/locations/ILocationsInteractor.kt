package com.majestadev.domain.interactors.locations

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.majestadev.domain.dto.LocationFilter
import com.majestadev.domain.entity.network.locations.LocationDetails

interface ILocationsInteractor {
    fun getLocations(): LiveData<PagingData<LocationDetails>>
    fun getFilteredLocations(locationFilter: LocationFilter): LiveData<PagingData<LocationDetails>>
    suspend fun getLocationById(id: Int): LocationDetails?
}