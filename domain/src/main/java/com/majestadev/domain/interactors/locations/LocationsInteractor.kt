package com.majestadev.domain.interactors.locations

import com.majestadev.domain.dto.LocationFilter
import com.majestadev.domain.repository.IRickAndMortyRepository
import javax.inject.Inject

class LocationsInteractor @Inject constructor(
    private val repository: IRickAndMortyRepository
) : ILocationsInteractor {

    override fun getLocations() = repository.getLocations()

    override fun getFilteredLocations(locationFilter: LocationFilter) =
        repository.getFilteredLocations(locationFilter)

    override suspend fun getLocationById(id: Int) = repository.getLocationById(id)
}