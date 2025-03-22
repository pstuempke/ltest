package com.stuempke.luzuatest.planets.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stuempke.luzuatest.domain.PlanetRepository
import com.stuempke.luzuatest.domain.model.Planet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class PlanetDetailScreenViewModel(
    savedStateHandle: SavedStateHandle,
    private val planetRepository: PlanetRepository,
) : ViewModel() {

    private val url = savedStateHandle.get<String>("url")
    private val _state: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)
    val state: StateFlow<ViewState> = _state

    init {
        viewModelScope.launch {
            url?.let { url ->
                planetRepository.getPlanet(url)
                    .onSuccess { planet -> _state.value = ViewState.Content(planet) }
                    .onFailure {
                        Timber.e(it)
                        _state.value = ViewState.Error(it.message ?: "An error occurred")
                    }
            } ?: run {
                _state.value = ViewState.Error("No planet name provided")
            }
        }
    }

    sealed interface ViewState {
        data object Loading : ViewState
        data class Content(val planet: Planet) : ViewState
        data class Error(val message: String) : ViewState
    }
}