package com.example.universaldownloader.MainUi

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import getDownloadedFiles
import kotlinx.coroutines.delay
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.example.universaldownloader.R

import java.io.File

@Composable
fun DownloadedFilesList(onFileClick: (File) -> Unit,modifier: Modifier) {
    val greencolor = Color(0xFF9ACD32)
    var files by remember { mutableStateOf<List<File>>(emptyList()) }
    val configuration = LocalConfiguration.current
    val height=(configuration.screenHeightDp-125)/7
    // Periodically refresh the list every few seconds
    LaunchedEffect(Unit) {
        while (true) {
            files = getDownloadedFiles()
            delay(2000) // check every 2 seconds
        }
    }
    if (files.isNotEmpty()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(22.dp),
            modifier = modifier
        ) {
            items(files, key = { it.absolutePath }) { file ->
                val isVideo = file.extension.lowercase() == "mp4"
                val isMusic = file.extension.lowercase() == "mp3"


                Card(
                    onClick = {
                        onFileClick(file)
                    },
                    colors = CardDefaults.cardColors(
                        containerColor = (Color(0xFF464246))
                    ),
                    modifier = Modifier.height(height.dp)

                    ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (isVideo) {
                            Icon(
                                painter = painterResource(R.drawable.video),
                                contentDescription = "Music",
                                tint = greencolor,
                                modifier = Modifier.padding(23.dp)
                                    .size(17.dp)
                            )
                        } else if (isMusic) {
                            Icon(
                                painter = painterResource(R.drawable.music),
                                contentDescription = "Video",
                                tint = greencolor,
                                modifier = Modifier.padding(23.dp)
                                    .size(17.dp)
                            )
                        }
                        Text(
                            text = file.name,
                            color = Color.White,
                            maxLines = 2,
                            fontFamily = FontFamily(Font(R.font.poppinslight)),
                            modifier = Modifier
                                .padding(16.dp)
                        )
                    }

                }

            }
        }
    }
    else{
        Column(
            verticalArrangement = Arrangement.spacedBy(180.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
            .padding(top=36.dp)
        ) {
            Text(text="Empty"
                , color = Color.White,
                fontFamily = FontFamily(Font(R.font.poppinslight)),
                fontSize = 30.sp,
            )
            Icon(
                painter = painterResource(R.drawable.empty),
                contentDescription = "",
                tint = (Color(0xFF464246)),
                modifier = Modifier.size(100.dp)

            )

        }
    }
}





fun playMedia(context: Context, file: File) {
    val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    val mimeType = when (file.extension.lowercase()) {
        "mp4" -> "video/mp4"
        "mp3" -> "audio/mpeg"
        else -> "*/*"
    }

    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, mimeType)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    context.startActivity(intent)
}



