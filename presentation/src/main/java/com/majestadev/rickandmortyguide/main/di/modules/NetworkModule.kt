package com.majestadev.rickandmortyguide.main.di.modules

import com.majestadev.data.network.Network
import com.majestadev.data.network.RickAndMortyApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provide(api: RickAndMortyApi) = Network(api)

    @Singleton
    @Provides
    fun provideApi(): RickAndMortyApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(RickAndMortyApi::class.java)
    }

    private companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"
    }
}