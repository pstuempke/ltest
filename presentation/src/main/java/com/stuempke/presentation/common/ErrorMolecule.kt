package com.stuempke.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun ErrorMolecule(
    modifier: Modifier = Modifier,
    message: String = "Retry",
    onRetry: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.error,
                shape = MaterialTheme.shapes.medium
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = message,
            modifier = Modifier.clickable { onRetry() },
            color = MaterialTheme.colorScheme.onError
        )

        IconButton(onClick = { onRetry() }) {
            Icon(imageVector = Icons.Filled.Refresh, contentDescription = "Retry", tint = MaterialTheme.colorScheme.onError)
        }
    }
}
