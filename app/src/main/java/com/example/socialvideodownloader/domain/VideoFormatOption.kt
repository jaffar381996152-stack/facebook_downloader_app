package com.example.socialvideodownloader.domain

data class VideoFormatOption(
    val id: String,
    val formatLabel: String,
    val resolution: String,
    val estimatedSize: String,
    val directUrl: String
)
