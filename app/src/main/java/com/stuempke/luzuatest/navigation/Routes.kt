package com.stuempke.luzuatest.navigation

import kotlinx.serialization.Serializable

sealed interface Routes {

    @Serializable
    data object PlanetList : Routes

    @Serializable
    data class PlanetDetails(val route: String = "eventDetails") : Routes

}