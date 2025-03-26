package com.stuempke.luzuatest.planets.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.stuempke.luzuatest.domain.PlanetRepository
import com.stuempke.luzuatest.domain.model.Planet
import com.stuempke.luzuatest.domain.onError
import com.stuempke.luzuatest.domain.onSuccess
import com.stuempke.luzuatest.navigation.Route
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlanetDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val planetRepository: PlanetRepository,
) : ViewModel() {

    private val url = savedStateHandle.toRoute<Route.PlanetDetails>().url
    private val _state: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)
    val state: StateFlow<ViewState> = _state

    init {
        loadPlanet()
    }

    fun retry() {
        loadPlanet()
    }

    private fun loadPlanet() {
        viewModelScope.launch {
            _state.value = ViewState.Loading
            planetRepository.getPlanet(url)
                .onSuccess { planet -> _state.value = ViewState.Content(planet) }
                .onError {
                    _state.value = ViewState.Error(it.toString())
                }
        }
    }

    sealed interface ViewState {
        data object Loading : ViewState
        data class Content(val planet: Planet) : ViewState
        data class Error(val message: String) : ViewState
    }
}