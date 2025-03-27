package com.stuempke.presentation.planetlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
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
import com.stuempke.presentation.R
import com.stuempke.presentation.common.ErrorMolecule
import com.stuempke.presentation.common.LoadingMolecule
import com.stuempke.presentation.model.PlanetUI
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun PlanetListScreen(viewModel: PlanetListViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    PlanetListScreenContent(state, viewModel::onViewAction)
}

@Preview
@Composable
fun PlanetListScreenContent(
    viewState: PlanetListViewModel.ViewState = PlanetListViewModel.ViewState.Loading,
    onViewAction: (PlanetListViewModel.ViewAction) -> Unit = {},
) {
    Surface {
        when (viewState) {
            PlanetListViewModel.ViewState.Loading -> {
                LoadingMolecule()
            }

            is PlanetListViewModel.ViewState.Content -> {
                PlanetList(viewState.planets, onViewAction)
            }

            is PlanetListViewModel.ViewState.Error -> {
                ErrorMolecule(
                    modifier = Modifier.padding(16.dp),
                    message = viewState.message,
                    onRetry = { onViewAction(PlanetListViewModel.ViewAction.Retry) })
            }
        }
    }
}


@Preview
@Composable
fun PlanetList(
    planets: List<PlanetUI> = emptyList(),
    onViewAction: (PlanetListViewModel.ViewAction) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier.padding(
            vertical = 16.dp
        ), verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        items(planets) {
            PlanetItem(
                planet = it,
                onClick = { onViewAction(PlanetListViewModel.ViewAction.PlanetSelected(it)) })
        }
    }
}

@Composable
private fun PlanetItem(
    modifier: Modifier = Modifier,
    planet: PlanetUI,
    onClick: () -> Unit
) {
    ListItem(
        modifier = modifier.clickable(onClick = onClick),
        headlineContent = {
            Text(text = planet.name, style = MaterialTheme.typography.headlineSmall)
        },
        supportingContent = {
            Column {
                Text(
                    text = stringResource(R.string.climate, planet.climate),
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = stringResource(R.string.population, planet.population),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        trailingContent = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Arrow"
            )
        }
    )
}


