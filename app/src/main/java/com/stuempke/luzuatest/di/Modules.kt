package com.stuempke.luzuatest.di

import com.stuempke.luzuatest.data.RemotePlanetDataSource
import com.stuempke.luzuatest.data.createHttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.stuempke.luzuatest.data.RemotePlanetDataSourceImpl
import com.stuempke.luzuatest.domain.PlanetRepository
import com.stuempke.luzuatest.domain.PlanetRepositoryImpl
import com.stuempke.luzuatest.navigation.NavigationManager
import com.stuempke.luzuatest.planets.ui.PlanetListViewModel
import com.stuempke.luzuatest.planets.ui.PlanetDetailViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.viewModelOf

import org.koin.dsl.bind


val navigationModule = module {
    singleOf(::NavigationManager)
}


val dispatcherModule = module {
    single { AppDispatchers(Dispatchers.Main, Dispatchers.IO, Dispatchers.Default) }
}

val mainModule = module {
    single { createHttpClient() }
    singleOf(::RemotePlanetDataSourceImpl).bind<RemotePlanetDataSource>()
    singleOf(::PlanetRepositoryImpl).bind<PlanetRepository>()

    viewModelOf(::PlanetListViewModel)
    viewModelOf(::PlanetDetailViewModel)
}
