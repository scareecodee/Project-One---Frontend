package com.example.universaldownloader.DataClasses

data class QualityResponse(
    val success: Boolean,
    val qualities: List<String>,
    val title: String,
    val error: String?
)
