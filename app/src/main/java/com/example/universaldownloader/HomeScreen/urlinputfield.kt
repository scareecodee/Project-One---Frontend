package com.example.universaldownloader.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.universaldownloader.R
import com.example.universaldownloader.ViewModels.ytviddownloadviewmodel

@Composable
fun urlinputfieldbox(
    modifier: Modifier,
    onClick: () -> Unit,
){
    val viewModel: ytviddownloadviewmodel = viewModel()
    var url by remember { mutableStateOf("") }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .height(150.dp)
            .background(Color(0xFF464246))
            .border(
                width = 0.5.dp,
                color = Color.White,
                shape = RoundedCornerShape(10.dp) // Optional, if you want rounded corners
            )
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            val containercolor=Color(0xFF787276)
            Text(
                text ="Paste Your Content Link Here",
                color = Color.White,
                fontSize = 15.sp,
                fontFamily=FontFamily(Font(R.font.poppinsextrabold)),
                modifier = Modifier.padding(top=25.5.dp, start =15.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 15.dp)
            ) {
                TextField(
                    value =url,
                    onValueChange = {url=it ;viewModel._url.value=it},
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.LightGray,
                        cursorColor = Color.White,
                        unfocusedContainerColor =containercolor,
                        focusedContainerColor =containercolor,
                        focusedIndicatorColor = Color.Transparent, // Removes the blue line
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    maxLines = 1,
                    placeholder = {
                        Text(
                            text = "Link Here ",
                            color = Color.DarkGray  ,// You can customize the hint color
                            maxLines = 1,
                            fontFamily = FontFamily(Font(R.font.poppinslight))
                        )
                    }
                   ,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.link),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.delete)
                            , contentDescription = "",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                                .clickable {
                                    url=""
                                    viewModel._url.value=""
                                }
                        )

                    },
                    modifier = Modifier.width(220.dp)
                )
                Button(
                    onClick = onClick,
                    enabled = if(url.isEmpty()) false else true,
                    colors = ButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.White,
                        disabledContentColor = Color.White,
                        disabledContainerColor = Color.Transparent
                    ),
                    modifier = Modifier.padding(start =2.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.proceed),
                        contentDescription = "",
                        tint = Color(0xFF9ACD32),
                        modifier = Modifier.size(35.dp)
                    )
                }
            }
        }
    }


}