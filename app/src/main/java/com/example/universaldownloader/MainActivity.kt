package com.example.universaldownloader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.universaldownloader.HomeScreen.HomeScreen
import com.example.universaldownloader.MainUi.mainscreen
import com.example.universaldownloader.ViewModels.OtpViewModel
import com.example.universaldownloader.ViewModels.ytviddownloadviewmodel


import com.example.universaldownloader.loginUi.loginScreen
import com.example.universaldownloader.otpUI.otpScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
           mainscreen()
        }
    }
}



@Composable
fun AppNavigation(viewModel: OtpViewModel = OtpViewModel()) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "loginscreen") {
        composable("loginScreen") {
             loginScreen(viewModel= viewModel,navController)
        }
        composable("otpScreen") {
             otpScreen(viewModel =viewModel,navController)
        }
        composable("mainscreen") {
           mainscreen()
        }
    }
}
