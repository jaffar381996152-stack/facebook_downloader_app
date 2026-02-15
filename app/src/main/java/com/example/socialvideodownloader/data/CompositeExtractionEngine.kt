package com.example.socialvideodownloader.data

import com.example.socialvideodownloader.domain.ExtractionResult

class CompositeExtractionEngine(
    private val engines: List<ExtractionEngine>
) : ExtractionEngine {

    override suspend fun extract(url: String): Result<ExtractionResult> {
        var lastError: Throwable? = null
        for (engine in engines) {
            val result = engine.extract(url)
            if (result.isSuccess) return result
            lastError = result.exceptionOrNull()
        }
        return Result.failure(lastError ?: IllegalStateException("No extraction engine available"))
    }
}
