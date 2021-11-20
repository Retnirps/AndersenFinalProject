package com.majestadev.rickandmortyguide.main.view.di.locations.details

import androidx.lifecycle.ViewModel
import com.majestadev.rickandmortyguide.main.di.ViewModelKey
import com.majestadev.rickandmortyguide.main.view.viewmodel.details.LocationDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface LocationDetailsFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(LocationDetailsViewModel::class)
    fun bindLocationDetailsViewModel(locationDetailsViewModel: LocationDetailsViewModel): ViewModel
}