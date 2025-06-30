package com.example.universaldownloader.retrofit

import com.example.universaldownloader.DataClasses.DirectLinkResponse
import com.example.universaldownloader.DataClasses.DownloadRequest
import com.example.universaldownloader.DataClasses.DownloadResponse
import com.example.universaldownloader.DataClasses.QualityResponse
import com.example.universaldownloader.DataClasses.VideoRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Streaming

interface VideoApiService {
    @POST("/video/download")
    fun downloadVideo(@Body request: DownloadRequest): Call<DownloadResponse>

    @GET("/video/download-file")
    @Streaming
    fun getMergedVideo(@Query("filename") filename: String): Call<ResponseBody>

    @POST("/video/get-qualities")
    fun getAvailableQualities(@Body request: VideoRequest): Call<QualityResponse>

}

