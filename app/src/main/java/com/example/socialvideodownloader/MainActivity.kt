package com.example.socialvideodownloader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.socialvideodownloader.ui.DownloaderScreen
import com.example.socialvideodownloader.ui.DownloaderViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: DownloaderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface {
                    DownloaderScreen(viewModel = viewModel)
                }
            }
        }
    }
}
