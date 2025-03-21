package com.stuempke.luzuatest

import android.app.Application
import com.stuempke.luzuatest.di.mainModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class LuziaTestApplication: Application() {

    override fun onCreate() {
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidContext(this@LuziaTestApplication)
            modules(mainModules)
        }
        super.onCreate()
    }
}