package com.example.socialvideodownloader.data

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import com.example.socialvideodownloader.domain.VideoFormatOption
import com.example.socialvideodownloader.domain.VideoPlatform

class VideoDownloadRepository(private val context: Context) {

    fun enqueueDownload(option: VideoFormatOption, platform: VideoPlatform): Long {
        require(option.directUrl.startsWith("https://") || option.directUrl.startsWith("http://")) {
            "Invalid download url"
        }

        val extension = option.formatLabel.lowercase().ifBlank { "mp4" }
        val request = DownloadManager.Request(Uri.parse(option.directUrl))
            .setTitle("${platform.displayName} • ${option.resolution}")
            .setDescription("Downloading ${option.formatLabel} (${option.estimatedSize})")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "social_videos/${platform.name.lowercase()}_${System.currentTimeMillis()}.$extension"
            )
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        return manager.enqueue(request)
    }
}
