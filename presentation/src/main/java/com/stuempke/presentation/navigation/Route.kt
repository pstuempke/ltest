package com.stuempke.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object PlanetList : Route

    @Serializable
    data class PlanetDetails(val url: String) : Route

}