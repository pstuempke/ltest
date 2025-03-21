package com.stuempke.luzuatest.planets.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stuempke.luzuatest.R
import com.stuempke.luzuatest.domain.model.Planet
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun PlanetListScreen(viewModel: PlanetListScreenViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    PlanetListScreenContent(state, viewModel::onViewAction)
}

@Preview
@Composable
fun PlanetListScreenContent(
    viewState: PlanetListScreenViewModel.ViewState = PlanetListScreenViewModel.ViewState.Loading,
    onViewAction: (PlanetListScreenViewModel.ViewAction) -> Unit = {},
) {
    Surface {
        when (viewState) {
            PlanetListScreenViewModel.ViewState.Loading -> {
                LoadingScreen()
            }

            is PlanetListScreenViewModel.ViewState.Content -> {
                PlanetList(viewState.planets, onViewAction)
            }

            is PlanetListScreenViewModel.ViewState.Error -> {
                // Error state
            }
        }
    }
}

@Preview
@Composable
fun PlanetList(
    planets: List<Planet> = emptyList(),
    onViewAction: (PlanetListScreenViewModel.ViewAction) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier.padding(
            vertical = 16.dp
        ), verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        items(planets) {
            PlanetItem(planet = it, onClick = { onViewAction(PlanetListScreenViewModel.ViewAction.PlanetSelected(it.name)) })
        }
    }
}

@Composable
fun PlanetItem(planet: Planet, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.fillMaxWidth().weight(1F)) {
            Text(text = planet.name, style = MaterialTheme.typography.headlineSmall)
            Text(
                text = stringResource(R.string.planetItemClimate, planet.climate),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = stringResource(R.string.planetItemPopulation, planet.population),
                style = MaterialTheme.typography.bodySmall
            )
        }
        Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Arrow")
    }
}

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
