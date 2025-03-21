package com.stuempke.luzuatest.di

import com.stuempke.luzuatest.data.RemotePlanetDataSource
import com.stuempke.luzuatest.data.createHttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.stuempke.luzuatest.data.RemotePlanetDataSourceImpl
import com.stuempke.luzuatest.domain.PlanetRepository
import com.stuempke.luzuatest.domain.PlanetRepositoryImpl
import com.stuempke.luzuatest.planets.ui.PlanetListScreenViewModel
import org.koin.core.module.dsl.viewModelOf

import org.koin.dsl.bind


val mainModules = module {
    single { createHttpClient() }
    singleOf(::RemotePlanetDataSourceImpl).bind<RemotePlanetDataSource>()
    singleOf(::PlanetRepositoryImpl).bind<PlanetRepository>()

    viewModelOf(::PlanetListScreenViewModel)
}
