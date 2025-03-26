package com.stuempke.presentation.planetlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stuempke.core.domain.onError
import com.stuempke.core.domain.onSuccess
import com.stuempke.presentation.navigation.NavigationManager
import com.stuempke.presentation.navigation.Route
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlanetListViewModel(
    private val navigationManager: NavigationManager,
    private val planetRepository: com.stuempke.core.domain.PlanetRepository
) : ViewModel() {

    private val _state: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)
    val state: StateFlow<ViewState> = _state

    init {
        getPlanets()
    }

    private fun getPlanets() {
        viewModelScope.launch {
            _state.value = ViewState.Loading
            planetRepository.getPlanets()
                .onSuccess { planets -> _state.value = ViewState.Content(planets) }
                .onError { error ->
                    _state.value = ViewState.Error(error.toString())
                }
        }
    }

    fun onViewAction(action: ViewAction) {
        viewModelScope.launch {
            when (action) {
                is ViewAction.PlanetSelected -> {
                    navigationManager.navigate(Route.PlanetDetails(url = action.url))
                }
                is ViewAction.Retry -> getPlanets()
            }
        }
    }

    sealed interface ViewAction {
        data object Retry : ViewAction
        data class PlanetSelected(val url: String) : ViewAction
    }

    sealed interface ViewState {
        data object Loading : ViewState
        data class Content(val planets: List<com.stuempke.core.domain.model.Planet>) : ViewState
        data class Error(val message: String) : ViewState
    }
}