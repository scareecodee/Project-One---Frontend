package com.example.universaldownloader.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.universaldownloader.R

@Composable
fun downloadingstatus(
    modifier: Modifier
){
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .height(100.dp)
            .background(Color(0xFF464246))
            .border(
                width = 0.5.dp,
                color = Color.White,
                shape = RoundedCornerShape(10.dp) // Optional, if you want rounded corners
            )
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
            , modifier = Modifier.fillMaxWidth()
        ) {
            val containercolor=Color(0xFF78727)
            val downloadbtncolor= Color(0xFF9ACD32)
            DownloadingStatusBar(
                progress = 1f
            )
            Text(
                text = "Location : Downloads/Videos"
                , fontSize = 14.sp,
                color = downloadbtncolor,
                fontFamily = FontFamily(Font(R.font.poppinslight))
            )
        }
    }
}


@Composable
fun DownloadingStatusBar(progress: Float) {
    // progress is in range 0.0–1.0
    val percentage = (progress * 100).toInt()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top=10.dp, bottom =8.dp, start = 15.dp, end = 15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val btnColor=Color(0xFF636AE8)
        Text(
            text =  if (percentage!=100) "Downloading Status:   $percentage%" else  "Download Status:   Completed",
            color = Color.White,
            fontSize =14.sp,
            fontFamily = FontFamily(Font(R.font.poppinsextrabold))
        )
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(7.dp),
            color =btnColor, // Progress color
            trackColor = Color.DarkGray,           // Background track
        )
    }
}
