package com.stuempke.presentation.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import com.stuempke.presentation.planetlist.PlanetListViewModel
import com.stuempke.presentation.planetdetail.PlanetDetailViewModel
import com.stuempke.presentation.navigation.NavigationManager
import com.stuempke.presentation.navigation.NavigationManagerImpl
import org.koin.dsl.bind

val navigationModule = module {
    singleOf(::NavigationManagerImpl).bind<NavigationManager>()
}

val mainModule = module {
    viewModelOf(::PlanetListViewModel)
    viewModelOf(::PlanetDetailViewModel)
}
