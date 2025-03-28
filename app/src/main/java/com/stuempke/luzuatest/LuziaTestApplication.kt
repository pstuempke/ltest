package com.stuempke.luzuatest

import android.app.Application
import com.stuempke.core.domain.di.coreModule
import com.stuempke.data.di.dataModule
import com.stuempke.data.di.dispatcherModule
import com.stuempke.presentation.di.mainModule
import com.stuempke.presentation.di.navigationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class LuziaTestApplication : Application() {

    override fun onCreate() {
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidContext(this@LuziaTestApplication)
            modules(navigationModule, mainModule, dispatcherModule, dataModule, coreModule)
        }
        super.onCreate()
    }
}