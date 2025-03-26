package com.stuempke.data.di

import com.stuempke.core.domain.PlanetRepository
import com.stuempke.data.RemotePlanetDataSource
import com.stuempke.data.createHttpClient
import com.stuempke.data.RemotePlanetDataSourceImpl
import com.stuempke.data.repository.PlanetRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val dataModule = module {
    single { createHttpClient() }
    singleOf(::RemotePlanetDataSourceImpl).bind<RemotePlanetDataSource>()
    singleOf(::PlanetRepositoryImpl).bind<PlanetRepository>()
}

val dispatcherModule = module {
    single { AppDispatchers(Dispatchers.Main, Dispatchers.IO, Dispatchers.Default) }
}
