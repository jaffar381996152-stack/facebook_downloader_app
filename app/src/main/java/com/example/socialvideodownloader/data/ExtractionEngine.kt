package com.example.socialvideodownloader.data

import com.example.socialvideodownloader.domain.ExtractionResult

interface ExtractionEngine {
    suspend fun extract(url: String): Result<ExtractionResult>
}
