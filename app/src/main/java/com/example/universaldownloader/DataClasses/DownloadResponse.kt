package com.example.universaldownloader.DataClasses

data class DownloadResponse(
    val success: Boolean,
    val error: String?,
    val filename: String // This must match backend response key
)
