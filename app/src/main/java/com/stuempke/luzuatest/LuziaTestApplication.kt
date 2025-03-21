package com.stuempke.luzuatest

import android.app.Application
import com.stuempke.luzuatest.di.mainModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class LuziaTestApplication: Application() {

    override fun onCreate() {
        startKoin {
            androidContext(this@LuziaTestApplication)
            modules(mainModules)
        }
        super.onCreate()
    }
}