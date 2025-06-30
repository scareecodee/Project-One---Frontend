package com.example.universaldownloader.retrofit

import com.example.universaldownloader.DataClasses.AudioRequest
import com.example.universaldownloader.DataClasses.AudioResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Streaming

interface AudioApiService {
    @POST("/audio/download")
    fun downloadAudio(@Body request: AudioRequest): Call<AudioResponse>

    @GET("/audio/file")
    @Streaming
    fun getAudioFile(@Query("filename") filename: String): Call<ResponseBody>
}
