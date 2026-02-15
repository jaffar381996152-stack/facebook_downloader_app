package com.example.socialvideodownloader.data

import com.example.socialvideodownloader.data.remote.ExtractRequest
import com.example.socialvideodownloader.data.remote.VideoExtractorApi
import com.example.socialvideodownloader.domain.ExtractionResult
import com.example.socialvideodownloader.domain.VideoFormatOption
import com.example.socialvideodownloader.domain.VideoPlatform

class RemoteExtractionEngine(
    private val api: VideoExtractorApi
) : ExtractionEngine {

    override suspend fun extract(url: String): Result<ExtractionResult> {
        return runCatching {
            val response = api.extract(ExtractRequest(url))
            ExtractionResult(
                platform = VideoPlatform.fromIdentifier(response.platform),
                options = response.downloadOptions.map { option ->
                    VideoFormatOption(
                        id = option.id,
                        formatLabel = option.format,
                        resolution = option.resolution,
                        estimatedSize = option.sizeLabel,
                        directUrl = option.downloadUrl
                    )
                }
            )
        }
    }
}
