package com.stuempke.presentation.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import com.stuempke.presentation.planetlist.PlanetListViewModel
import com.stuempke.presentation.planetdetail.PlanetDetailViewModel
import com.stuempke.presentation.navigation.NavigationManager

val navigationModule = module {
    singleOf(::NavigationManager)
}

val mainModule = module {
    viewModelOf(::PlanetListViewModel)
    viewModelOf(::PlanetDetailViewModel)
}
