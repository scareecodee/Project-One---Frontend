package com.example.universaldownloader.loginUi

import retrofit2.Call
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.universaldownloader.R
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import com.example.universaldownloader.DataClasses.EmailRequest
import com.example.universaldownloader.DataClasses.OtpResponse
import com.example.universaldownloader.ViewModels.OtpViewModel
import com.example.universaldownloader.retrofit.RetrofitClient
import com.example.universaldownloader.ViewModels.ytviddownloadviewmodel
import kotlinx.coroutines.delay
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun loginScreen(viewModel: OtpViewModel, navController: NavController){
    val bgColor= Color(0xFF322D31)
    val btnColor=Color(0xFF636AE8)
    val context = LocalContext.current
    val ytviewModel: ytviddownloadviewmodel = viewModel()
    var secondsLeft by remember { mutableStateOf(20) }
    var isContinueButtonEnabled by remember { mutableStateOf(true) }


    var email by remember { mutableStateOf("") }
    val iconList = listOf(R.drawable.youtube, R.drawable.instagram,R.drawable.facebook,R.drawable.utorrent)
    ConstraintLayout (
        modifier =Modifier.fillMaxSize()
            .background(bgColor)
            .verticalScroll(enabled = true, state = rememberScrollState())
            .imePadding()
    ){
        val (textRef,headingRef,loginbtnref,pleaseloginref, iconrow,emailInputField)= createRefs()
        Text(
            text = "Log in to Zebyte",
            fontSize = 25.sp,
            color = Color.White,
            fontWeight = FontWeight.W800,
            fontFamily = FontFamily(Font(R.font.poppinsextrabold)) ,
            modifier = Modifier.padding(top=60.dp)
                .constrainAs(headingRef){
                top.linkTo(parent.top)
                bottom.linkTo(textRef.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }

        )
        Text(
            text ="Download Youtube videos & music, Instagram & Facebook Reels, stories,posts    -   All in one place , quickly and easily!"
            ,fontSize =18.sp,
            color =Color.White,
            maxLines = 7,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(Font(R.font.poppinslight)) ,
            modifier = Modifier.padding( top =130.dp, bottom = 5.dp, start = 20.dp, end = 20.dp)
                .constrainAs(textRef){
                top.linkTo(headingRef.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }

        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(40.dp),
            modifier = Modifier.padding(30.dp)
                .constrainAs(iconrow){
                    top.linkTo(textRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(top=5.dp)
        ) {
            items(iconList) { iconRes ->
                Icon(
                    painter = painterResource(iconRes),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(33.dp)
                )
            }
        }
        EmailInput(
            email=email,
            onEmailChange = {email=it},
            modifier = Modifier.padding(top =40.dp)
                .constrainAs(emailInputField){
                top.linkTo(iconrow.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Button(
            enabled = if (!isValidEmail(email) || email.isEmpty()) false else true,
            onClick = {
                isContinueButtonEnabled=false
                    ytviewModel.isLoading.value=true
                    var otpResult =""
                    val request = EmailRequest(email)
                    viewModel._email.value=email
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
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = btnColor,
                disabledContainerColor = Color.Red,
                disabledContentColor = Color.Black,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier.padding(60.dp)
                .constrainAs(loginbtnref){
                    top.linkTo(emailInputField.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .width(230.dp)
                .height(48.dp)

        ) {
            Text(
                text = if (isContinueButtonEnabled) "Continue" else "Wait",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
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
        LaunchedEffect(key1 = isContinueButtonEnabled) {
            if (!isContinueButtonEnabled) {
                while (secondsLeft > 0) {
                    delay(1000L)
                    secondsLeft--
                }
                isContinueButtonEnabled = true
                secondsLeft = 20
            }
        }
    }
}


