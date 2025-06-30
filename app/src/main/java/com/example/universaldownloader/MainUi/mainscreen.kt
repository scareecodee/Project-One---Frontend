package com.example.universaldownloader.MainUi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.universaldownloader.HomeScreen.BottomNavBar
import com.example.universaldownloader.HomeScreen.HomeScreen
import com.example.universaldownloader.HomeScreen.TopNav
import com.example.universaldownloader.ViewModels.ytviddownloadviewmodel
import androidx.compose.ui.platform.LocalContext


@Composable
fun mainscreen(){
    val context = LocalContext.current
    val ytviewModel: ytviddownloadviewmodel = viewModel()
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
            .background(Color(0xFF322D31))
    ) {
        val (bottomnavbarref,homescreenref,statusbarref,upcomingscreenref,topnavref,filelistref)=createRefs()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(
                    Color(0xFF666164)
                )
                .constrainAs(statusbarref){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

       if(ytviewModel._indexfrombottomnav.value==0){  // homescreen
               TopNav(
                   modifier = Modifier.constrainAs(topnavref){
                       top.linkTo(statusbarref.bottom)
                       start.linkTo(parent.start)
                       end.linkTo(parent.end)
                   }
               )
           if(ytviewModel._indexfromtopnav.value==0){   // youtube
               HomeScreen(
                   modifier = Modifier.fillMaxSize()
                       .constrainAs(homescreenref){
                       top.linkTo(topnavref.bottom)
                       start.linkTo(parent.start)
                       end.linkTo(parent.end)
                   }
               )
           }
           else if(ytviewModel._indexfromtopnav.value==1){   // instagram)
               upcomingscreens(
                   modifier = Modifier
                       .constrainAs(upcomingscreenref){
                           top.linkTo(topnavref.bottom)
                           bottom.linkTo(bottomnavbarref.top)
                           start.linkTo(parent.start)
                           end.linkTo(parent.end)
                       }
               )
       }
           else if(ytviewModel._indexfromtopnav.value==2){   // facebook
               upcomingscreens(
                   modifier = Modifier
                       .constrainAs(upcomingscreenref){
                           top.linkTo(topnavref.bottom)
                           bottom.linkTo(bottomnavbarref.top)
                           start.linkTo(parent.start)
                           end.linkTo(parent.end)
                       }
               )
           }
           else{         //torrent
               upcomingscreens(
                   modifier = Modifier
                       .constrainAs(upcomingscreenref){
                           top.linkTo(topnavref.bottom)
                           bottom.linkTo(bottomnavbarref.top)
                           start.linkTo(parent.start)
                           end.linkTo(parent.end)
                       }
               )
           }


       }
        else if(ytviewModel._indexfrombottomnav.value==1) {     // clicked on downloads
           DownloadedFilesList(onFileClick = { file ->
               // Action when a file is clicked (e.g., play video)
               playMedia(context, file)
           },
               modifier = Modifier
                   .padding(top=50.dp,bottom = 20.dp, start = 15.dp, end = 15.dp)
                   .constrainAs(filelistref){
                       top.linkTo(statusbarref.bottom)
                       end.linkTo(parent.end)
                       start.linkTo(parent.start)
                   }
                   .fillMaxSize()


           )
       }

       else{             // clicked on About Us

     }

        BottomNavBar(
            modifier =Modifier.constrainAs(bottomnavbarref)
            {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}