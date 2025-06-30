package com.example.universaldownloader.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitClient {
    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS) // increase to 60 seconds ---> command ipconfig in cmd prompt
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(logging)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.123.75.137:8000")   // your ipv4 address ---> command ipconfig in cmd prompt
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.128.71.198:8000")  // your ipv4 address ---> command ipconfig in cmd prompt
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }


    val videoApi: VideoApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.138.75.1974:8000")   // your ipv4 address ---> command ipconfig in cmd prompt
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VideoApiService::class.java)
    }
    val audioapi: AudioApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.75.194:8000")   // your ipv4 address ---> command ipconfig in cmd prompt
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(AudioApiService::class.java)
    }

}

