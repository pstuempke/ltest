package com.stuempke.presentation.planetdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.stuempke.core.domain.onError
import com.stuempke.core.domain.onSuccess
import com.stuempke.presentation.navigation.Route
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlanetDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val planetRepository: com.stuempke.core.domain.PlanetRepository,
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
        data class Content(val planet: com.stuempke.core.domain.model.Planet) : ViewState
        data class Error(val message: String) : ViewState
    }
}