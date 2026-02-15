package com.example.socialvideodownloader.domain

enum class VideoPlatform(val displayName: String) {
    FACEBOOK("Facebook"),
    TWITTER("Twitter / X"),
    PINTEREST("Pinterest"),
    INSTAGRAM("Instagram"),
    DIRECT("Direct Link"),
    UNKNOWN("Unknown");

    companion object {
        fun fromIdentifier(raw: String): VideoPlatform {
            return when (raw.lowercase()) {
                "facebook" -> FACEBOOK
                "twitter", "x" -> TWITTER
                "pinterest" -> PINTEREST
                "instagram" -> INSTAGRAM
                "direct" -> DIRECT
                else -> UNKNOWN
            }
        }

        fun fromUrl(url: String): VideoPlatform {
            val lowered = url.lowercase()
            return when {
                lowered.contains("facebook.com") || lowered.contains("fb.watch") -> FACEBOOK
                lowered.contains("twitter.com") || lowered.contains("x.com") -> TWITTER
                lowered.contains("pinterest.com") || lowered.contains("pin.it") -> PINTEREST
                lowered.contains("instagram.com") -> INSTAGRAM
                lowered.endsWith(".mp4") || lowered.endsWith(".webm") || lowered.contains(".mp4?") -> DIRECT
                else -> UNKNOWN
            }
        }
    }
}
