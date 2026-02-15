package com.example.socialvideodownloader.data

import com.example.socialvideodownloader.data.remote.ApiClientFactory
import com.example.socialvideodownloader.domain.ExtractionResult

class VideoOptionsFactory {

    private val extractionEngine: ExtractionEngine = CompositeExtractionEngine(
        engines = listOf(
            LocalDirectUrlExtractionEngine(),
            RemoteExtractionEngine(ApiClientFactory.extractorApi())
        )
    )

    suspend fun fetchOptions(sourceUrl: String): Result<ExtractionResult> {
        return extractionEngine.extract(sourceUrl)
    }
}
