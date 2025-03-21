package com.stuempke.luzuatest.planets.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stuempke.luzuatest.domain.PlanetRepository
import com.stuempke.luzuatest.domain.model.Planet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class PlanetListScreenViewModel(private val planetRepository: PlanetRepository) : ViewModel() {

    val state: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)

    init {
        viewModelScope.launch {
            planetRepository.getPlanets()
                .onSuccess { planets -> state.value = ViewState.Content(planets) }
                .onFailure {
                    Timber.e(it)
                    this@PlanetListScreenViewModel.state.value = ViewState.Error(it.message ?: "An error occurred")
                }
        }
    }

    fun onViewAction(action: ViewAction) {
        Timber.d("OnAction: $action")
        when (action) {
            is ViewAction.PlanetSelected -> {
                // Handle planet selection
            }
        }
    }

    sealed interface ViewAction {
        data class PlanetSelected(val name: String) : ViewAction
    }

    sealed interface ViewState {
        data object Loading : ViewState
        data class Content(val planets: List<Planet>) : ViewState
        data class Error(val message: String) : ViewState
    }
}