package com.majestadev.rickandmortyguide.main.view.viewmodel.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majestadev.domain.entity.network.characters.CharacterDetails
import com.majestadev.domain.entity.network.locations.LocationDetails
import com.majestadev.domain.interactors.characters.ICharactersInteractor
import com.majestadev.domain.interactors.locations.LocationsInteractor
import com.majestadev.domain.util.RegexUtil
import com.majestadev.rickandmortyguide.main.di.Injector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationDetailsViewModel @Inject constructor(
    val locationsInteractor: LocationsInteractor,
    val charactersInteractor: ICharactersInteractor
) : ViewModel() {

    private val _locationDetailsLiveData = MutableLiveData<LocationDetails>()
    val locationDetailsLiveData: LiveData<LocationDetails>
        get() = _locationDetailsLiveData
    private val _residentsLiveData = MutableLiveData<List<CharacterDetails>>()
    val residentsLiveData: LiveData<List<CharacterDetails>>
        get() = _residentsLiveData

    fun getLocationById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val locationDetails = locationsInteractor.getLocationById(id)

            locationDetails?.let {
                _locationDetailsLiveData.postValue(it)

                val residentsIds = ArrayList<Int>()
                locationDetails.residents.forEach { url ->
                    residentsIds.add(RegexUtil.extractIdFromUrl(url))
                }

                if (residentsIds.size == 1) {
                    getResidentById(residentsIds[0])
                } else {
                    getMultipleResidents(
                        locationDetails.id,
                        RegexUtil.extractFromBrackets(residentsIds.toString())
                    )
                }
            }
        }
    }

    private suspend fun getMultipleResidents(locationId: Int, residentsIds: String) {
        val residents =
            charactersInteractor.getMultipleCharactersInLocation(locationId, residentsIds)

        _residentsLiveData.postValue(residents)
    }

    private suspend fun getResidentById(id: Int) {
        val resident = charactersInteractor.getCharacterById(id)

        resident?.let {
            _residentsLiveData.postValue(listOf(it))
        }
    }

    override fun onCleared() {
        super.onCleared()
        Injector.clearLocationDetailsFragmentComponent()
    }
}