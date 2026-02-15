package com.example.socialvideodownloader.data

import java.net.URI

class PlatformDetector {

    fun isValidHttpUrl(url: String): Boolean {
        return runCatching {
            val parsed = URI(url.trim())
            (parsed.scheme == "http" || parsed.scheme == "https") && !parsed.host.isNullOrBlank()
        }.getOrDefault(false)
    }
}
