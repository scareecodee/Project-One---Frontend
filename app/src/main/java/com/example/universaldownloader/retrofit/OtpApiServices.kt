package com.example.universaldownloader.retrofit



import com.example.universaldownloader.DataClasses.EmailRequest
import com.example.universaldownloader.DataClasses.OtpResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call

interface ApiService {
    @POST("/send_otp/")
    fun sendOtp(@Body request: EmailRequest): Call<OtpResponse>
}
