package com.stuempke.presentation.planetlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stuempke.core.domain.onError
import com.stuempke.core.domain.onSuccess
import com.stuempke.presentation.model.PlanetUI
import com.stuempke.presentation.model.toPresentation
import com.stuempke.presentation.navigation.NavigationManager
import com.stuempke.presentation.navigation.Route
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PlanetListViewModel(
    private val navigationManager: NavigationManager,
    private val planetRepository: com.stuempke.core.domain.PlanetRepository
) : ViewModel() {

    private val _state: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)
    val state: StateFlow<ViewState> = _state.onStart {
        getPlanets()
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = _state.value
    )

    private fun getPlanets() {
        viewModelScope.launch {
            _state.value = ViewState.Loading
            planetRepository.getPlanets()
                .onSuccess { planets ->
                    _state.value = ViewState.Content(planets.map { it.toPresentation() })
                }
                .onError { error ->
                    // Here we can resolve to any string we find appropriate to show to the user
                    _state.value = ViewState.Error(error.toString())
                }
        }
    }

    fun onViewAction(action: ViewAction) {
        viewModelScope.launch {
            when (action) {
                is ViewAction.PlanetSelected -> {
                    navigationManager.navigate(
                        // technically we could pass the whole planet object here, but I prefer viewmodles to work with just an id or url
                        Route.PlanetDetails(
                            url = action.planet.url,
                        )
                    )
                }
                is ViewAction.Retry -> getPlanets()
            }
        }
    }

    sealed interface ViewAction {
        data object Retry : ViewAction
        data class PlanetSelected(val planet: PlanetUI) : ViewAction
    }

    sealed interface ViewState {
        data object Loading : ViewState
        data class Content(val planets: List<PlanetUI>) : ViewState
        data class Error(val message: String) : ViewState
    }
}