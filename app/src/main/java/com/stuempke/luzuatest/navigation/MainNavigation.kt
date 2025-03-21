package com.stuempke.luzuatest.navigation

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stuempke.luzuatest.planets.ui.PlanetDetailsScreen
import com.stuempke.luzuatest.planets.ui.PlanetListScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(modifier = Modifier.statusBarsPadding(), navController = navController, startDestination = Routes.PlanetList) {
        composable<Routes.PlanetList> {
            PlanetListScreen()
        }
        composable<Routes.PlanetDetails> {
            PlanetDetailsScreen()
        }
    }
}
