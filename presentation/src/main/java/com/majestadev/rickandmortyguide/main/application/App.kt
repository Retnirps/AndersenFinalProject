package com.majestadev.rickandmortyguide.main.application

import android.app.Application
import com.majestadev.rickandmortyguide.main.di.Injector

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        Injector.init(this)
    }
}