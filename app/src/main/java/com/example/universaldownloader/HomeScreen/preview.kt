package com.example.universaldownloader.HomeScreen

import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.universaldownloader.DataClasses.AudioRequest
import com.example.universaldownloader.DataClasses.AudioResponse
import com.example.universaldownloader.DataClasses.DownloadRequest
import com.example.universaldownloader.DataClasses.DownloadResponse
import com.example.universaldownloader.R
import com.example.universaldownloader.retrofit.RetrofitClient.audioapi
import com.example.universaldownloader.retrofit.RetrofitClient.videoApi
import com.example.universaldownloader.ViewModels.ytviddownloadviewmodel
import downloadAudioToPhone
import downloadMergedVideoFromBackend
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun preview(
    modifier:Modifier
) {
    val context = LocalContext.current
    var viewModel: ytviddownloadviewmodel = viewModel()
        var selectedIndex by remember { mutableStateOf(0) }
    val cardbgcolor=(Color(0xFF464246))
    val greencolor= Color(0xFF9ACD32)
    var selectedicon by remember { mutableStateOf(0) }
    var icon1tint by remember { mutableStateOf(greencolor) }
        var icon2tint by remember { mutableStateOf(Color.White) }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .height(340.dp)
            .background(Color(0xFF464246))
            .border(
                width = 0.5.dp,
                color = Color.White,
                shape = RoundedCornerShape(10.dp) // Optional, if you want rounded corners
            )
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(0.dp),
        ) {
            val containercolor = Color(0xFF787276)
            Row(
                modifier = Modifier.padding(top = 21.dp, start = 15.dp)
                , horizontalArrangement = Arrangement.spacedBy(30.dp)
            ){
                Text(
                    text = "Your Content Preview",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.poppinsextrabold)),

                )
               Icon(
                   painter = painterResource(R.drawable.video),
                   contentDescription = "",
                   tint = icon1tint,
                   modifier = Modifier.size(20.dp)
                       .clickable {
                           icon1tint= greencolor
                           icon2tint=Color.White
                           selectedicon=0
                           viewModel._selectedicon.value=selectedicon
                       }
               )
                Icon(
                    painter = painterResource(R.drawable.music),
                    contentDescription = "",
                    tint = icon2tint,
                    modifier = Modifier.size(20.dp)
                        .clickable {
                            icon1tint= Color.White
                            icon2tint=greencolor
                            selectedicon=1
                            viewModel._selectedicon.value=selectedicon
                        }
                )

            }
                  Box(
                      modifier = Modifier
                          .padding(10.dp)
                          .fillMaxWidth()
                          .height(0.2.dp)
                          .background(Color.White)
                  )

            YoutubeThumbnail(
                videoUrl = viewModel._url.value,
                modifier = Modifier
                        .clip( shape = RoundedCornerShape(30.dp))
                        .padding(top=15.dp,end = 20.dp, start = 20.dp)
                        .width(500.dp)
                        .height(150.dp)

            )
                Row (
                    horizontalArrangement= Arrangement.spacedBy(20.dp),
                    modifier = Modifier.padding(start = 15.dp,end=15.dp,
                        top =30.dp)
                ) {
                    val btnColor=Color(0xFF636AE8)
                    Button(
                        enabled = if (viewModel._quality.value=="NaN" && viewModel._selectedicon.value==0) false else true,
                        onClick = {

                            if(viewModel._selectedicon.value==0) {

                                Log.d("url", viewModel._url.value)
                                Log.d("quality sending to server is", viewModel._quality.value)
                                videoApi.downloadVideo(
                                    DownloadRequest(
                                        video_url = viewModel._url.value,
                                        quality = viewModel._quality.value
                                    )
                                )
                                    .enqueue(object : Callback<DownloadResponse> {
                                        override fun onResponse(
                                            call: Call<DownloadResponse>,
                                            response: Response<DownloadResponse>
                                        ) {
                                            if (response.isSuccessful) {
                                                Toast.makeText(context,"Preparing....",Toast.LENGTH_SHORT).show()
                                                val filename = response.body()?.filename
                                                if (filename != null) {
                                                    downloadMergedVideoFromBackend(
                                                        context,
                                                        filename
                                                    )

                                                }
                                                else{
                                                    Toast.makeText(context,"Download Failed",Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        }

                                        override fun onFailure(
                                            call: Call<DownloadResponse>,
                                            t: Throwable
                                        ) {
                                            Toast.makeText(context,"Download Failed",Toast.LENGTH_SHORT).show()
                                        }
                                    })
                            }

                            else{
                                val videoUrl=viewModel._url.value
                                Log.d("url sending for audio",videoUrl)
                                audioapi.downloadAudio(AudioRequest(videoUrl)).enqueue(object : Callback<AudioResponse> {
                                    override fun onResponse(call: Call<AudioResponse>, response: Response<AudioResponse>) {
                                        if (response.isSuccessful) {
                                            response.body()?.filename?.let { filename ->
                                                Log.d("AudioDownload", "Filename: $filename")
                                                downloadAudioToPhone(context, filename)
                                            }
                                        }
                                        else{
                                            Toast.makeText(context,"Download Failed",Toast.LENGTH_SHORT).show()
                                        }
                                    }

                                    override fun onFailure(call: Call<AudioResponse>, t: Throwable) {
                                        Toast.makeText(context,"Download Failed",Toast.LENGTH_SHORT).show()
                                    }
                                })

                            }
                        },
                        colors = ButtonColors(
                            containerColor = btnColor,
                            contentColor = Color.White,
                            disabledContentColor = Color.White,
                            disabledContainerColor = btnColor
                        )
                        , shape = RoundedCornerShape(13.dp),
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Download",
                                color =Color.White,
                                fontSize =14.sp,
                                fontFamily = FontFamily(Font(R.font.poppinslight))
                            )
                            Icon(
                                painter = painterResource(R.drawable.download),
                                contentDescription = "",
                                tint =Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    var selectedQuality by remember { mutableStateOf("NaN") }
                    QualityDropdown(
                        selectedQuality =selectedQuality,
                        qualityOptions = viewModel._qualitieslist.value,
                        onQualitySelected = { newSelectedQuality ->
                            selectedQuality=newSelectedQuality
                            viewModel._quality.value=selectedQuality
                            Log.d("quality Selected is",viewModel._quality.value)
                        }
                    )
                }

            }

        }
    }




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QualityDropdown(
    qualityOptions: List<String> = listOf("1080p", "720p", "480p", "360p"),
    selectedQuality: String,
    onQualitySelected: (String) -> Unit
) {
    var viewModel: ytviddownloadviewmodel = viewModel()
    var expanded by remember { mutableStateOf(false) }

    androidx.compose.material3.ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            if (viewModel._selectedicon.value == 0) {
                expanded = !expanded
            }
        }
        , modifier = Modifier.width(130.dp)
    ) {
        val containercolor=Color(0xFF787276)
        TextField(
            enabled = viewModel._selectedicon.value==0,
            value = selectedQuality,
            onValueChange = {
            },
            readOnly = true,
            label = { Text(text = "Quality",
                maxLines = 1,
                fontFamily = FontFamily(Font(R.font.poppinslight)),
                color = Color.DarkGray
            ) },
            trailingIcon = {
                androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.LightGray,
                disabledContainerColor =Color(0xFF666164) ,
                cursorColor = Color.White,
                unfocusedContainerColor =containercolor,
                focusedContainerColor =containercolor,
                focusedIndicatorColor = Color.Transparent, // Removes the blue line
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.menuAnchor()
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = containercolor
        ) {
            qualityOptions.forEach { quality ->
                androidx.compose.material3.DropdownMenuItem(
                    enabled = viewModel._selectedicon.value==0,
                    text = { Text(
                        text = quality
                        , fontFamily = FontFamily(Font(R.font.poppinslight)),
                        color = Color.White
                    )
                           }
                    ,
                    onClick = {
                        onQualitySelected(quality)
                        expanded = false
                    }
                )
            }
        }
    }
}

fun extractYoutubeVideoId(url: String): String? {
    val regex = Regex("""(?:v=|\/)([0-9A-Za-z_-]{11}).*""")
    return regex.find(url)?.groupValues?.get(1)
}

@Composable
fun YoutubeThumbnail(
    videoUrl: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val videoId = remember(videoUrl) { extractYoutubeVideoId(videoUrl) }

    if (videoId != null) {
        val thumbnailUrl = "https://img.youtube.com/vi/$videoId/hqdefault.jpg"

        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(thumbnailUrl)
                .crossfade(true)
                .build(),
            contentDescription = "YouTube Thumbnail",
            modifier = modifier
        )
    } else {
        Icon(
            painter = painterResource(id = R.drawable.notfound), // Add parrot image to res/drawable
            contentDescription = "Fallback Image",
            tint = Color.White,
            modifier = modifier
        )
    }
}