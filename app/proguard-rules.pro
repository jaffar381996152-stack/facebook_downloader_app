# Keep Kotlin serialization classes for Retrofit
-keepclassmembers,allowobfuscation class * {
    @kotlinx.serialization.SerialName <fields>;
}
-keep,includedescriptorclasses class com.example.socialvideodownloader.data.remote.** { *; }
-dontwarn kotlinx.serialization.**
