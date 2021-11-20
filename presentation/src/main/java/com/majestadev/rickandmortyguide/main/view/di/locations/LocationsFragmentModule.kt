package com.majestadev.rickandmortyguide.main.view.di.locations

import androidx.lifecycle.ViewModel
import com.majestadev.rickandmortyguide.main.di.ViewModelKey
import com.majestadev.rickandmortyguide.main.view.viewmodel.LocationsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface LocationsFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(LocationsViewModel::class)
    fun bindLocationsViewModel(locationsViewModel: LocationsViewModel): ViewModel
}