package com.stuempke.luzuatest.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class NavigationManager {
    private val _navigationEvents = MutableSharedFlow<NavigationEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    suspend fun navigate(route: Route) {
        _navigationEvents.emit(NavigationEvent.NavigateTo(route))
    }

    suspend fun popBackStack() {
        _navigationEvents.emit(NavigationEvent.PopBackStack)
    }
}


sealed class NavigationEvent {
    data class NavigateTo(val route: Route) : NavigationEvent()
    data object PopBackStack : NavigationEvent()
}