package com.stuempke.luzuatest.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object PlanetList : Route

    @Serializable
    data class PlanetDetails(val url: String) : Route

}