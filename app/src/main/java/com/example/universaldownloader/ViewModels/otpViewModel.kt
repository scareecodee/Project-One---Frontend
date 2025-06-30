package com.example.universaldownloader.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

// OtpViewModel.kt
class OtpViewModel : ViewModel() {
    var _otpByServer = mutableStateOf(0)
    var _areAllFilled = mutableStateOf(false)
    var _isLoggedIn = mutableStateOf(false)
        var verifyBtnClicked = mutableStateOf(false)
            var _email= mutableStateOf("")
        private set

    fun setOtp(value: Int) {
        _otpByServer.value = value
    }

    fun setAllFilled(value: Boolean){
        _areAllFilled.value=value
    }

    fun setIsLoggedIn(value: Boolean){
        _isLoggedIn.value=value
    }
}
