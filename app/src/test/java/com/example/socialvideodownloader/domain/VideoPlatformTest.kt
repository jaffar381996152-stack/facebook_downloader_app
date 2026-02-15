package com.example.socialvideodownloader.domain

import org.junit.Assert.assertEquals
import org.junit.Test

class VideoPlatformTest {

    @Test
    fun `maps known platform identifiers`() {
        assertEquals(VideoPlatform.INSTAGRAM, VideoPlatform.fromIdentifier("instagram"))
        assertEquals(VideoPlatform.TWITTER, VideoPlatform.fromIdentifier("x"))
        assertEquals(VideoPlatform.FACEBOOK, VideoPlatform.fromIdentifier("facebook"))
        assertEquals(VideoPlatform.PINTEREST, VideoPlatform.fromIdentifier("pinterest"))
        assertEquals(VideoPlatform.DIRECT, VideoPlatform.fromIdentifier("direct"))
    }

    @Test
    fun `maps unknown platform identifiers`() {
        assertEquals(VideoPlatform.UNKNOWN, VideoPlatform.fromIdentifier("linkedin"))
    }

    @Test
    fun `maps platform from url`() {
        assertEquals(VideoPlatform.FACEBOOK, VideoPlatform.fromUrl("https://facebook.com/watch?v=1"))
        assertEquals(VideoPlatform.DIRECT, VideoPlatform.fromUrl("https://cdn.example.com/video.mp4"))
    }
}
