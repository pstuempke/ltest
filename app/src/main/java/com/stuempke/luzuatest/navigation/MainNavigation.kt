package com.stuempke.luzuatest.navigation

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stuempke.luzuatest.planets.ui.PlanetDetailsScreen
import com.stuempke.luzuatest.planets.ui.PlanetListScreen
import org.koin.compose.getKoin


@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val navigationManager: NavigationManager = getKoin().get()

    NavigationEffectHandler(navController, navigationManager)

    NavHost(
        modifier = Modifier.statusBarsPadding(),
        navController = navController,
        startDestination = Route.PlanetList
    ) {
        composable<Route.PlanetList> {
            PlanetListScreen()
        }
        composable<Route.PlanetDetails> {
            PlanetDetailsScreen()
        }
    }
}

@Composable
private fun NavigationEffectHandler(
    navController: androidx.navigation.NavHostController,
    navigationManager: NavigationManager
) {
    LaunchedEffect(navigationManager) {
        navigationManager.navigationEvents.collect { event ->
            when (event) {
                is NavigationEvent.NavigateTo -> navController.navigate(event.route)
                NavigationEvent.PopBackStack -> navController.popBackStack()
            }
        }
    }
}
