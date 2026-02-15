package com.example.socialvideodownloader.domain

data class ExtractionResult(
    val platform: VideoPlatform,
    val options: List<VideoFormatOption>
)
