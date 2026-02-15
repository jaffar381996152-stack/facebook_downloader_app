package com.example.socialvideodownloader.data

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test

class LocalDirectUrlExtractionEngineTest {

    private val engine = LocalDirectUrlExtractionEngine()

    @Test
    fun `extract succeeds for direct media links`() = runBlocking {
        val result = engine.extract("https://cdn.example.com/path/video.mp4")
        assertTrue(result.isSuccess)
    }

    @Test
    fun `extract fails for non-media links`() = runBlocking {
        val result = engine.extract("https://instagram.com/reel/abc")
        assertTrue(result.isFailure)
    }
}
