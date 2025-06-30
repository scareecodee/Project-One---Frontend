package com.example.universaldownloader.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.universaldownloader.R
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.universaldownloader.ViewModels.ytviddownloadviewmodel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
import com.example.universaldownloader.DataClasses.QualityResponse
import com.example.universaldownloader.DataClasses.VideoRequest
import com.example.universaldownloader.retrofit.RetrofitClient.videoApi


@Composable
fun  HomeScreen(
    modifier: Modifier=Modifier
){
    val viewModel: ytviddownloadviewmodel = viewModel()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.background(Color(0xFF322D31)
        )
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color(0xFF322D31)
                )
        ){
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                urlinputfieldbox(
                    modifier=Modifier.padding(vertical = 40.dp, horizontal =20.dp),
                    onClick = {
                        viewModel._quality.value="NaN"
                        viewModel.isLoading.value=true
                        viewModel._fetchbtnclicked.value=true
                        viewModel._qualitieslist.value= listOf("NaN","NaN","NaN","NaN","NaN")
                        videoApi.getAvailableQualities(VideoRequest(viewModel._url.value))
                            .enqueue(object : Callback<QualityResponse> {
                                override fun onResponse(call: Call<QualityResponse>, response: Response<QualityResponse>) {
                                    if (response.isSuccessful) {
                                        viewModel.isLoading.value=false
                                        val qualitiesList = response.body()?.qualities
                                        val body = response.body()
                                        Log.d("heyy", "Prased body = $body")
                                        if (qualitiesList != null) {
                                            if (qualitiesList.isNotEmpty()) {
                                                viewModel.setQualitiesList(qualitiesList)
                                            }
                                        }
                                    }
                                }
                                override fun onFailure(call: Call<QualityResponse>, t: Throwable) {
                                    viewModel.isLoading.value=false
                                    Log.e("QualitiesFetch", "Failure: ${t.message}")
                                }
                            })
                    },
                    )
                if (viewModel.isLoading.value) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.padding(20.dp)
                    )
                    Text(
                        text = "Fetching Data..."
                        , color = Color.White,
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.poppinslight))
                    )
                } else {
                    if(viewModel._fetchbtnclicked.value && viewModel._url.value.isNotEmpty()) {
                        preview(
                            modifier = Modifier.padding(start = 20.dp, end = 20.dp)
                        )
                    }
                }

            }
        }

    }
}

@Composable
fun eachcolumn(
    icon: Painter,
    title:String,
    isSelected: Boolean,
    istop:Boolean=true,
    isBottom:Boolean
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val greencolor= Color(0xFF9ACD32)
       var mainbgcolor= Color(0xFF322D31)
        val btnColor=Color(0xFF1C2E4A)
        Icon(
            painter = icon,
            contentDescription = "",
            tint = if(istop ) {
                if(isSelected)
                    mainbgcolor
                else
                    Color.White
            }
                    else if(isBottom){
                         if(isSelected)
                             btnColor
                     else
                    Color.White
                    }

                    else{
                        Color.Green
            },
            modifier = Modifier.size(
                if(istop){
                    if(isSelected)
                        26.dp
                    else
                        20.dp
                }
                else
                20.dp
            )
        )
        Text(
            text = title,
            color = if(isSelected) Color.Black else Color.White,
            fontSize =if(isSelected) 13.sp else 11.sp,
            fontFamily = FontFamily(Font(R.font.poppinslight))
        )
    }
}


@Composable
fun eachButtonOfRow(
    title: String,
    painter: Painter,
    isSelected: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier,
    istop: Boolean=true ,
    isBottom:Boolean
){
    Button(
        shape = RectangleShape,
        onClick = onClick,
        colors = ButtonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Unspecified,
            disabledContentColor = Color.Unspecified,
            disabledContainerColor = Color.Unspecified
        )
        , modifier = modifier
    ) {
        eachcolumn(
            icon = painter,
            title = title,
            isSelected=isSelected,
            istop=istop,
            isBottom=isBottom
        )
    }
}


@Composable
fun TopNav(
    modifier: Modifier
){
    val ytviewModel: ytviddownloadviewmodel = viewModel()
    Box(
        modifier =modifier
            .clip(RoundedCornerShape(bottomStart = 18.dp, bottomEnd = 18.dp))
            .fillMaxWidth()
            .height(87.dp)
            .background(Color(0xFF787276))

    ){
        ConstraintLayout(
            modifier =Modifier.fillMaxWidth()
        ) {
            val selectedIndex = remember { mutableIntStateOf(-1) }
            val (headingref,rowref)=createRefs()
            val configuration = LocalConfiguration.current
            val itemWidth = (configuration.screenWidthDp/3).dp
            LazyRow(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .padding(7.dp)
                    .constrainAs(rowref) {
                        top.linkTo(headingref.bottom, margin = 10.dp)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                item {
                    eachButtonOfRow(
                        title = "Youtube",
                        painter = painterResource(R.drawable.youtube),
                        isSelected = selectedIndex.intValue == 0,
                        onClick = { selectedIndex.intValue = 0
                                  ytviewModel._indexfromtopnav.intValue=0},
                        modifier = Modifier.width(itemWidth),
                        isBottom = false
                    )
                }
                item {
                    eachButtonOfRow(
                        title = "Instagram",
                        painter = painterResource(R.drawable.instagram),
                        isSelected = selectedIndex.intValue == 1,
                        onClick = { selectedIndex.intValue = 1
                            ytviewModel._indexfromtopnav.intValue=1
                                  },
                        modifier = Modifier.width(itemWidth),
                        isBottom = false
                    )
                }
                item {
                    eachButtonOfRow(
                        title = "Facebook",
                        painter = painterResource(R.drawable.facebook),
                        isSelected = selectedIndex.intValue == 2,
                        onClick = { selectedIndex.intValue = 2
                            ytviewModel._indexfromtopnav.intValue=2},
                        modifier = Modifier.width(itemWidth),
                        isBottom = false
                    )
                }
                item {
                    eachButtonOfRow(
                        title = "Torrent",
                        painter = painterResource(R.drawable.utorrent),
                        isSelected = selectedIndex.intValue == 3,
                        onClick = { selectedIndex.intValue = 3
                            ytviewModel._indexfromtopnav.intValue=3},
                        modifier = Modifier.width(itemWidth),
                        isBottom = false
                    )
                }
            }
        }
    }
}
