# Social Video Downloader (Android)

Production Android app built with **Kotlin**, **Jetpack Compose**, **MVVM**, and **Gradle Kotlin DSL**.

## SDK targets
- Min SDK: **24**
- Target SDK: **34**
- Compile SDK: **34**

## Current production behavior
The app is now functional in two modes:
1. **Direct-link mode (works out of the box):** if user pastes a direct media URL (`.mp4`, `.webm`, etc.), app can download immediately.
2. **Extractor-service mode (recommended for social links):** app calls configured backend (`VIDEO_API_BASE_URL`) to resolve social-page URLs into direct downloadable media URLs.

This removes the previous hard dependency where the app could not function at all without backend.

## Architecture
- `VideoOptionsFactory` → composite extraction strategy
  - `LocalDirectUrlExtractionEngine` (on-device direct-link extraction)
  - `RemoteExtractionEngine` (backend API extraction)
- `DownloaderViewModel` handles URL validation, loading/error states, and download flow
- `VideoDownloadRepository` queues downloads through Android `DownloadManager`

## Backend endpoint (optional but recommended)
Set in `app/build.gradle.kts`:

```kotlin
buildConfigField("String", "VIDEO_API_BASE_URL", '"https://api.example.com/"')
```

Expected API:
- `POST /v1/extract`
- body:
```json
{ "url": "https://..." }
```
- response:
```json
{
  "platform": "instagram",
  "download_options": [
    {
      "id": "1",
      "format": "mp4",
      "resolution": "1080p",
      "size_label": "20 MB",
      "download_url": "https://cdn.example/video.mp4"
    }
  ]
}
```

## Wrapper note (for PR systems that block binaries)
This repository intentionally does **not** commit `gradle/wrapper/gradle-wrapper.jar` to avoid PR systems that reject binary files.

- `./gradlew` and `gradlew.bat` automatically bootstrap `gradle-wrapper.jar` on first run.
- CI still works the same; the wrapper jar is fetched at runtime.

## Build locally
```bash
./gradlew assembleDebug
```

## GitHub Actions APK
`.github/workflows/android-build.yml`:
1. Runs unit tests
2. Builds debug APK
3. Uploads `app-debug.apk` artifact
