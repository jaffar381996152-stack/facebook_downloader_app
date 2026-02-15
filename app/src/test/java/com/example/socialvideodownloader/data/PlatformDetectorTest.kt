package com.example.socialvideodownloader.data

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PlatformDetectorTest {

    private val detector = PlatformDetector()

    @Test
    fun `validates http and https urls`() {
        assertTrue(detector.isValidHttpUrl("https://instagram.com/reel/abc"))
        assertTrue(detector.isValidHttpUrl("http://facebook.com/watch?v=1"))
    }

    @Test
    fun `rejects invalid urls`() {
        assertFalse(detector.isValidHttpUrl("instagram.com/reel/abc"))
        assertFalse(detector.isValidHttpUrl("not-a-url"))
    }
}
