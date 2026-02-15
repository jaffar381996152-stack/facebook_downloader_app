package com.example.socialvideodownloader.data.remote

import com.example.socialvideodownloader.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory

object ApiClientFactory {

    fun extractorApi(): VideoExtractorApi {
        val logging = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.ENABLE_NETWORK_LOGS) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val json = Json {
            ignoreUnknownKeys = true
            explicitNulls = false
        }

        return Retrofit.Builder()
            .baseUrl(BuildConfig.VIDEO_API_BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(VideoExtractorApi::class.java)
    }
}
