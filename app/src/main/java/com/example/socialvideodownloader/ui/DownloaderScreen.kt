package com.example.socialvideodownloader.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.socialvideodownloader.domain.VideoFormatOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloaderScreen(viewModel: DownloaderViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Social Video Downloader") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = uiState.inputUrl,
                onValueChange = viewModel::onUrlChanged,
                label = { Text("Video link") },
                placeholder = { Text("Paste Facebook, Twitter/X, Pinterest, or Instagram URL") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Button(
                onClick = viewModel::analyzeUrl,
                enabled = !uiState.isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Analyze link")
            }

            if (uiState.isLoading) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            Text(
                text = "Platform: ${uiState.detectedPlatform.displayName}",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = uiState.statusMessage,
                style = MaterialTheme.typography.bodyMedium
            )

            if (uiState.options.isNotEmpty()) {
                Text("Available download options", style = MaterialTheme.typography.titleMedium)
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f, fill = false)
                ) {
                    items(uiState.options, key = { it.id }) { option ->
                        OptionRow(
                            option = option,
                            isSelected = option.id == uiState.selectedOption?.id,
                            onSelect = { viewModel.onOptionSelected(option) }
                        )
                    }
                }
            }

            Button(
                onClick = viewModel::startDownload,
                enabled = uiState.selectedOption != null && !uiState.isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Download selected format")
            }
        }
    }
}

@Composable
private fun OptionRow(
    option: VideoFormatOption,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        onClick = onSelect,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("${option.formatLabel} • ${option.resolution}", style = MaterialTheme.typography.titleSmall)
                Text("Estimated: ${option.estimatedSize}", style = MaterialTheme.typography.bodySmall)
            }
            RadioButton(selected = isSelected, onClick = onSelect)
        }
    }
}
