package com.majestadev.rickandmortyguide.main.view.di.characters

import com.majestadev.rickandmortyguide.main.di.FragmentScope
import com.majestadev.rickandmortyguide.main.fragment.characters.CharactersFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [CharactersFragmentModule::class])
interface CharactersFragmentComponent {
    fun inject(charactersFragment: CharactersFragment)
}