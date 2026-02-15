package com.example.socialvideodownloader.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialvideodownloader.data.PlatformDetector
import com.example.socialvideodownloader.data.VideoDownloadRepository
import com.example.socialvideodownloader.data.VideoOptionsFactory
import com.example.socialvideodownloader.domain.VideoFormatOption
import com.example.socialvideodownloader.domain.VideoPlatform
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DownloaderUiState(
    val inputUrl: String = "",
    val detectedPlatform: VideoPlatform = VideoPlatform.UNKNOWN,
    val options: List<VideoFormatOption> = emptyList(),
    val selectedOption: VideoFormatOption? = null,
    val isLoading: Boolean = false,
    val statusMessage: String = "Paste a social video link and tap Analyze."
)

class DownloaderViewModel(application: Application) : AndroidViewModel(application) {

    private val detector = PlatformDetector()
    private val optionsFactory = VideoOptionsFactory()
    private val downloadRepository = VideoDownloadRepository(application)

    private val _uiState = MutableStateFlow(DownloaderUiState())
    val uiState: StateFlow<DownloaderUiState> = _uiState.asStateFlow()

    fun onUrlChanged(url: String) {
        _uiState.update {
            it.copy(
                inputUrl = url.trim(),
                statusMessage = if (url.isBlank()) {
                    "Paste a social video link and tap Analyze."
                } else {
                    it.statusMessage
                }
            )
        }
    }

    fun analyzeUrl() {
        val url = _uiState.value.inputUrl

        if (!detector.isValidHttpUrl(url)) {
            _uiState.update {
                it.copy(
                    options = emptyList(),
                    selectedOption = null,
                    detectedPlatform = VideoPlatform.UNKNOWN,
                    statusMessage = "Please enter a valid http(s) URL."
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    options = emptyList(),
                    selectedOption = null,
                    statusMessage = "Analyzing link and fetching formats..."
                )
            }

            val result = optionsFactory.fetchOptions(url)
            result.onSuccess { extraction ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        detectedPlatform = extraction.platform,
                        options = extraction.options,
                        selectedOption = extraction.options.firstOrNull(),
                        statusMessage = if (extraction.options.isEmpty()) {
                            "No downloadable formats found for this link."
                        } else {
                            "Detected ${extraction.platform.displayName}. Select a format and download."
                        }
                    )
                }
            }.onFailure { _ ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        detectedPlatform = VideoPlatform.UNKNOWN,
                        statusMessage = "Could not extract downloadable media. Paste a direct video URL or configure backend extractor service."
                    )
                }
            }
        }
    }

    fun onOptionSelected(option: VideoFormatOption) {
        _uiState.update { it.copy(selectedOption = option) }
    }

    fun startDownload() {
        val state = _uiState.value
        val option = state.selectedOption ?: return

        viewModelScope.launch {
            runCatching {
                downloadRepository.enqueueDownload(option, state.detectedPlatform)
            }.onSuccess {
                _uiState.update {
                    it.copy(statusMessage = "Download started. Check notifications and Downloads/social_videos.")
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(statusMessage = "Could not queue download: ${error.message.orEmpty()}")
                }
            }
        }
    }
}
