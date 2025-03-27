package com.stuempke.presentation.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

interface NavigationManager {
    val navigationEvents: SharedFlow<NavigationEvent>
    suspend fun navigate(route: Route)
    suspend fun popBackStack()
}

class NavigationManagerImpl : NavigationManager {
    private val _navigationEvents = MutableSharedFlow<NavigationEvent>()
    override val navigationEvents = _navigationEvents.asSharedFlow()

    override suspend fun navigate(route: Route) {
        _navigationEvents.emit(NavigationEvent.NavigateTo(route))
    }

    override suspend fun popBackStack() {
        _navigationEvents.emit(NavigationEvent.PopBackStack)
    }
}

sealed class NavigationEvent {
    data class NavigateTo(val route: Route) : NavigationEvent()
    data object PopBackStack : NavigationEvent()
}