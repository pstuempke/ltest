package com.stuempke.presentation.planetdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.stuempke.core.domain.PlanetRepository
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

class PlanetDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val planetRepository: PlanetRepository,
    private val navigationManager: NavigationManager
) : ViewModel() {

    private val url = savedStateHandle.toRoute<Route.PlanetDetails>().url
    private val _state: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)
    val state: StateFlow<ViewState> = _state.onStart {
        loadPlanet()
    }.stateIn(
        scope = viewModelScope,
        initialValue = _state.value,
        started = SharingStarted.WhileSubscribed(5000L),
        )

    fun onViewAction(viewAction: ViewAction) = viewModelScope.launch {
        when (viewAction) {
            is ViewAction.Retry -> loadPlanet()
            is ViewAction.Back -> navigationManager.popBackStack()
        }
    }

    private fun loadPlanet() {
        viewModelScope.launch {
            _state.value = ViewState.Loading
            planetRepository.getPlanet(url)
                .onSuccess { planet -> _state.value = ViewState.Content(planet.toPresentation()) }
                .onError {
                    // Here we can resolve to any string we find appropriate to show to the user
                    _state.value = ViewState.Error(it.toString())
                }
        }
    }

    sealed interface ViewState {
        data object Loading : ViewState
        data class Content(val planet: PlanetUI) : ViewState
        data class Error(val message: String) : ViewState
    }

    sealed class ViewAction {
        data object Back : ViewAction()
        data object Retry : ViewAction()
    }
}