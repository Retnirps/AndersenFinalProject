package com.majestadev.rickandmortyguide.main.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.majestadev.domain.dto.LocationFilter
import com.majestadev.domain.interactors.locations.LocationsInteractor
import com.majestadev.rickandmortyguide.main.di.Injector
import javax.inject.Inject

class LocationsViewModel @Inject constructor(
    val locationsInteractor: LocationsInteractor
) : ViewModel() {

    fun getLocations() = locationsInteractor.getLocations().cachedIn(viewModelScope)

    fun getFilteredLocations(locationFilter: LocationFilter) =
        locationsInteractor.getFilteredLocations(locationFilter).cachedIn(viewModelScope)

    override fun onCleared() {
        super.onCleared()
        Injector.clearLocationsFragmentComponent()
    }
}