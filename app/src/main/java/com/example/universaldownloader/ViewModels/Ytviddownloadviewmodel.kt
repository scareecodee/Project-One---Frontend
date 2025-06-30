package com.example.universaldownloader.ViewModels

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ytviddownloadviewmodel: ViewModel() {
        var _url= mutableStateOf("")
            var _quality= mutableStateOf("NaN")
                var _selectedicon= mutableIntStateOf(0)
                    var _qualitieslist= mutableStateOf(listOf<String>())
    var _indexfrombottomnav= mutableIntStateOf(0)
    var _indexfromtopnav= mutableIntStateOf(0)
    var _fetchbtnclicked=mutableStateOf(false)
    var isLoading = mutableStateOf(false)

        private set

    fun setQualitiesList(value : List<String>){
        _qualitieslist.value=value
    }

}