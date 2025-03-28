package com.stuempke.core.domain.di

import com.stuempke.core.domain.PlanetRepository
import com.stuempke.core.domain.repository.PlanetRepositoryImpl

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val coreModule = module {
    singleOf(::PlanetRepositoryImpl).bind<PlanetRepository>()
}