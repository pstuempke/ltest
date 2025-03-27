package com.stuempke.presentation.planetdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stuempke.presentation.R
import com.stuempke.presentation.common.BackNavigationIcon
import com.stuempke.presentation.common.ErrorMolecule
import com.stuempke.presentation.common.LoadingMolecule
import com.stuempke.presentation.common.MainTopAppBar
import com.stuempke.presentation.model.PlanetUI
import com.stuempke.presentation.planetdetail.PlanetDetailViewModel.*
import org.koin.androidx.compose.koinViewModel

@Composable
fun PlanetDetailsScreen(viewModel: PlanetDetailViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    PlanetDetailsScreenContent(state = state, onAction = viewModel::onViewAction)
}

@Preview
@Composable
fun PlanetDetailsScreenContent(
    state: ViewState = ViewState.Loading,
    onAction: (ViewAction) -> Unit = { }
) {
    Surface {
        when (state) {
            ViewState.Loading -> {
                LoadingMolecule()
            }

            is ViewState.Content -> {
                PlanetDetail(planet = state.planet, onAction = onAction)
            }

            is ViewState.Error -> {
                ErrorMolecule(
                    message = state.message,
                    onRetry = { onAction(ViewAction.Retry) })
            }
        }
    }
}


@Composable
private fun PlanetDetail(planet: PlanetUI, onAction: (ViewAction) -> Unit = { }) {
    Scaffold(topBar = {
        MainTopAppBar(
            title = planet.name,
            navigationIcon = { BackNavigationIcon { onAction(ViewAction.Back) } })
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.details_subtitle),
                style = MaterialTheme.typography.titleLarge
            )
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
}

@Preview
@Composable
fun PlanetDetailPreview() {
    PlanetDetail(
        planet = PlanetUI()
    )
}