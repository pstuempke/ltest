package com.stuempke.luzuatest.planets.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stuempke.luzuatest.R
import com.stuempke.luzuatest.domain.model.Planet
import com.stuempke.luzuatest.ui.components.ErrorMolecule
import com.stuempke.luzuatest.ui.components.LoadingMolecule
import org.koin.androidx.compose.koinViewModel

@Preview
@Composable
fun PlanetDetailsScreen(viewModel: PlanetDetailScreenViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    PlanetDetailsScreenContent(state)
}

@Preview
@Composable
fun PlanetDetailsScreenContent(state: PlanetDetailScreenViewModel.ViewState = PlanetDetailScreenViewModel.ViewState.Loading) {
    Surface {
        when (state) {
            PlanetDetailScreenViewModel.ViewState.Loading -> {
                LoadingMolecule()
            }

            is PlanetDetailScreenViewModel.ViewState.Content -> {
                PlanetDetail(state.planet)
            }

            is PlanetDetailScreenViewModel.ViewState.Error -> {
                ErrorMolecule(message = state.message, onRetry = { })
            }
        }
    }
}

@Composable
private fun PlanetDetail(planet: Planet) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = planet.name, style = MaterialTheme.typography.headlineLarge)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = stringResource(R.string.gravity, planet.gravity),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = stringResource(R.string.population, planet.population),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = stringResource(R.string.climate, planet.climate),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = stringResource(R.string.terrain, planet.terrain),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = stringResource(R.string.diameter, planet.diameter),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
