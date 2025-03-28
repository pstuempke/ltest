package com.stuempke.data.di

import com.stuempke.core.domain.AppDispatchers
import com.stuempke.core.domain.datasource.RemotePlanetDataSource
import com.stuempke.data.createHttpClient
import com.stuempke.data.RemotePlanetDataSourceImpl
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val dataModule = module {
    single { createHttpClient() }
    singleOf(::RemotePlanetDataSourceImpl).bind<RemotePlanetDataSource>()
}

val dispatcherModule = module {
    single { AppDispatchers(Dispatchers.Main, Dispatchers.IO, Dispatchers.Default) }
}
