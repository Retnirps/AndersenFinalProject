package com.majestadev.rickandmortyguide.main.view.di.locations.details

import com.majestadev.rickandmortyguide.main.di.FragmentScope
import com.majestadev.rickandmortyguide.main.fragment.locations.details.LocationDetailsFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [LocationDetailsFragmentModule::class])
interface LocationDetailsFragmentComponent {
    fun inject(locationDetailsFragment: LocationDetailsFragment)
}