package com.stuempke.presentation.navigation

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.stuempke.presentation.planetdetail.PlanetDetailsScreen
import com.stuempke.presentation.planetlist.PlanetListScreen
import org.koin.compose.getKoin


@ExperimentalMaterial3Api
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
    navController: NavHostController,
    navigationManager: NavigationManager
) {
    LaunchedEffect(navigationManager) {
        navigationManager.navigationEvents.collect { event ->
            when (event) {
                is NavigationEvent.NavigateTo -> navController.navigate(event.route)
                is NavigationEvent.PopBackStack -> navController.popBackStack()
            }
        }
    }
}
