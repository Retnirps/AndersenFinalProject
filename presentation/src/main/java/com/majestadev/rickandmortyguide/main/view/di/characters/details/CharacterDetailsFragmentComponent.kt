package com.majestadev.rickandmortyguide.main.view.di.characters.details

import com.majestadev.rickandmortyguide.main.di.FragmentScope
import com.majestadev.rickandmortyguide.main.fragment.characters.details.CharacterDetailsFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [CharacterDetailsFragmentModule::class])
interface CharacterDetailsFragmentComponent {
    fun inject(characterDetailsFragment: CharacterDetailsFragment)
}