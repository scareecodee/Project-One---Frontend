package com.example.universaldownloader.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.universaldownloader.R
import com.example.universaldownloader.ViewModels.ytviddownloadviewmodel

@Composable
fun BottomNavBar(
    modifier: Modifier
) {
    val ytviewModel: ytviddownloadviewmodel = viewModel()
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(75.dp)
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
                        title = "Home",
                        painter = painterResource(R.drawable.home),
                        isSelected = selectedIndex.intValue == 0,
                        onClick = { selectedIndex.intValue = 0
                            ytviewModel._indexfrombottomnav.value=0
                                  },
                        modifier = Modifier.width(itemWidth),
                        istop = false,
                        isBottom = true
                    )
                }
                item {
                    eachButtonOfRow(
                        title = "Downloads",
                        painter = painterResource(R.drawable.downloads),
                        isSelected = selectedIndex.intValue == 1,
                        onClick = { selectedIndex.intValue = 1
                            ytviewModel._indexfrombottomnav.value=1},
                        modifier = Modifier.width(itemWidth),
                        istop = false,
                        isBottom = true
                    )
                }
                item {
                    eachButtonOfRow(
                        title = "About Us",
                        painter = painterResource(R.drawable.aboutus),
                        isSelected = selectedIndex.intValue == 2,
                        onClick = { selectedIndex.intValue = 2
                            ytviewModel._indexfrombottomnav.value=2},
                        modifier = Modifier.width(itemWidth),
                        istop = false,
                        isBottom = true
                    )
                }
            }
        }
    }
}

