package com.majestadev.rickandmortyguide.main.view.di.locations

import com.majestadev.rickandmortyguide.main.di.FragmentScope
import com.majestadev.rickandmortyguide.main.fragment.locations.LocationsFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [LocationsFragmentModule::class])
interface LocationsFragmentComponent {
    fun inject(locationsFragment: LocationsFragment)
}