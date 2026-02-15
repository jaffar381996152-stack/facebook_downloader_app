package com.example.socialvideodownloader.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.POST

interface VideoExtractorApi {
    @POST("v1/extract")
    suspend fun extract(@Body request: ExtractRequest): ExtractResponse
}

@Serializable
data class ExtractRequest(
    val url: String
)

@Serializable
data class ExtractResponse(
    val platform: String,
    @SerialName("download_options")
    val downloadOptions: List<DownloadOptionDto>
)

@Serializable
data class DownloadOptionDto(
    val id: String,
    val format: String,
    val resolution: String,
    @SerialName("size_label")
    val sizeLabel: String,
    @SerialName("download_url")
    val downloadUrl: String
)
