package com.example.socialvideodownloader.data

import android.webkit.MimeTypeMap
import com.example.socialvideodownloader.domain.ExtractionResult
import com.example.socialvideodownloader.domain.VideoFormatOption
import com.example.socialvideodownloader.domain.VideoPlatform

class LocalDirectUrlExtractionEngine : ExtractionEngine {

    override suspend fun extract(url: String): Result<ExtractionResult> {
        return runCatching {
            if (!looksLikeDirectVideo(url)) {
                throw IllegalArgumentException("No direct media stream found from the pasted link.")
            }

            val extension = MimeTypeMap.getFileExtensionFromUrl(url).ifBlank { "mp4" }
            val option = VideoFormatOption(
                id = "local-direct",
                formatLabel = extension.uppercase(),
                resolution = "Original",
                estimatedSize = "Unknown",
                directUrl = url
            )
            ExtractionResult(
                platform = VideoPlatform.fromUrl(url),
                options = listOf(option)
            )
        }
    }

    private fun looksLikeDirectVideo(url: String): Boolean {
        val lowered = url.lowercase()
        return lowered.endsWith(".mp4") || lowered.endsWith(".webm") || lowered.endsWith(".mkv") ||
            lowered.contains(".mp4?") || lowered.contains(".webm?") || lowered.contains("video")
    }
}
