@file:OptIn(ExperimentalMaterial3Api::class)

package com.stuempke.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

@Composable
fun MainTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    navigationIcon: @Composable (() -> Unit)? = null
) {
    CenterAlignedTopAppBar(
        modifier = modifier.shadow(elevation = 4.dp),
        title = { Text(text = title, style = MaterialTheme.typography.headlineLarge) },
        navigationIcon = {
            navigationIcon?.invoke()
        }
    )
}

@Composable
fun BackNavigationIcon(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back"
        )
    }
}

@Composable
fun CloseNavigationIcon(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close"
        )
    }
}
