package com.example.universaldownloader.otpUI

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import com.example.universaldownloader.DataClasses.EmailRequest
import com.example.universaldownloader.DataClasses.OtpResponse
import com.example.universaldownloader.ViewModels.OtpViewModel
import com.example.universaldownloader.R
import com.example.universaldownloader.ViewModels.ytviddownloadviewmodel
import com.example.universaldownloader.retrofit.RetrofitClient
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Response

@Composable
fun otpScreen(viewModel: OtpViewModel, navController: NavController){
    val context = LocalContext.current
    val otpFromServer = viewModel._otpByServer.value
    var otpByUser by remember { mutableIntStateOf(0)}
    val bgColor= Color(0xFF322D31)
    val btnColor= Color(0xFF636AE8)
    var isLogined by remember { mutableStateOf(false) }
    var secondsLeft by remember { mutableStateOf(30) }
    ConstraintLayout(
        modifier=Modifier.fillMaxSize()
            .background(bgColor)

    ) {
        val ytviewModel: ytviddownloadviewmodel = viewModel()
        val email=viewModel._email.value
        val (headingref, otpinputref,verifybtnref,resendbtnref)=createRefs()
        Text(
            text = "OTP Verification !!!",
            fontSize = 25.sp,
            color = Color.White,
            fontWeight = FontWeight.W500,
            fontFamily = FontFamily(Font(R.font.poppinsextrabold)) ,
            modifier = Modifier.padding(top =72.dp)
                .constrainAs(headingref){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        OtpInput(
            length = 6,
            onOtpComplete = { otp ->
                otpByUser=otp.toInt()
                if (otpByUser== otpFromServer){
                    isLogined=true
                }
            },
            modifier = Modifier.padding(top =40.dp)
                .constrainAs(otpinputref){
                    top.linkTo(headingref.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            viewModel = viewModel
        )
        Button(
            onClick = {
                     if(otpByUser== otpFromServer){

                         Toast.makeText(context,"Login Successfull",Toast.LENGTH_LONG).show()
                         viewModel._isLoggedIn.value=true
                         if(viewModel._isLoggedIn.value){
                             navController.navigate("mainscreen")
                         }
                     }
                else
                     {
                         Toast.makeText(context,"Invalid OTP",Toast.LENGTH_LONG).show()
                         viewModel._areAllFilled.value=false
                         viewModel._isLoggedIn.value=false
                     }

            },
            enabled =viewModel._areAllFilled.value ,
            colors = ButtonDefaults.buttonColors(
                containerColor = btnColor,
                disabledContainerColor = Color.Red,
                disabledContentColor = Color.Black,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier.padding(top =55.dp)
                .constrainAs(verifybtnref){
                    top.linkTo(otpinputref.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .width(200.dp)
                .height(48.dp)
        ) {
            Text(text = "Verify",
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.poppinsextrabold)),
            )
        }

        var isResendButtonEnabled by remember { mutableStateOf(true) }
         Button(
             enabled = isResendButtonEnabled,
             modifier = Modifier.constrainAs(resendbtnref){
                 top.linkTo(verifybtnref.bottom, margin = 20.dp)
                 start.linkTo(parent.start)
                 end.linkTo(parent.end)
             },
             colors = ButtonDefaults.buttonColors(
                 containerColor = btnColor,
                 disabledContainerColor = Color.Red,
                 disabledContentColor = Color.Black,
                 contentColor = Color.Black
             ),
             onClick = {
                 ytviewModel.isLoading.value=true
                 isResendButtonEnabled=false
                 var otpResult =""
                 val request = EmailRequest(email)

                 RetrofitClient.api.sendOtp(request).enqueue(object : retrofit2.Callback<OtpResponse> {
                     @androidx.annotation.OptIn(UnstableApi::class)
                     override fun onResponse(
                         call:
                         Call<OtpResponse>,
                         response: Response<OtpResponse>
                     ) {
                         if (response.isSuccessful) {
                             val  otp = response.body()?.otp
                             if (otp != null) {
                                 isResendButtonEnabled=false
                                 ytviewModel.isLoading.value=false
                                 viewModel.setOtp(otp)
                             }
                             Toast.makeText(context, "OTP sent to $email", Toast.LENGTH_SHORT).show()
                             navController.navigate("otpScreen")

                         } else {
                             ytviewModel.isLoading.value=false
                             otpResult = "Error: ${response.code()}"
                             Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show()
                         }
                     }

                     @androidx.annotation.OptIn(UnstableApi::class)
                     override fun onFailure(call: Call<OtpResponse>, t: Throwable) {
                         ytviewModel.isLoading.value=false
                         Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show()
                     }
                 })
             }
         ) {
             Text(
                 text = if (isResendButtonEnabled) "Resend OTP" else "Resend in ${secondsLeft}s",
                 fontFamily = FontFamily(Font(R.font.poppinslight))
             )

             if (ytviewModel.isLoading.value) {
                 CircularProgressIndicator(
                     color = Color.White,
                     strokeWidth = 1.4.dp,
                     modifier = Modifier.padding(start = 10.dp)
                         .size(20.dp)
                 )
             }
         }

        LaunchedEffect(key1 = isResendButtonEnabled) {
            if (!isResendButtonEnabled) {
                while (secondsLeft > 0) {
                    delay(1000L)
                    secondsLeft--
                }
                isResendButtonEnabled = true
                secondsLeft = 30
            }
        }

    }
}