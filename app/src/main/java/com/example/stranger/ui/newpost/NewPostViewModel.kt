package com.example.stranger.ui.newpost

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stranger.mission
import com.example.stranger.missionListImg
import com.example.stranger.model.ItemHome
import com.example.stranger.repository.Repository
import kotlinx.coroutines.launch

class NewPostViewModel : ViewModel() {
    private val repository :Repository = Repository()
    fun addItemHome(key: String, itemHome: ItemHome, mission: mission)= viewModelScope.launch {
        repository.addItemHome(key,itemHome, mission)
    }
    fun getKey()= repository.getkey()
    fun getUser ()= repository.getUserLogin()
    fun upListImg(listUri: ArrayList<Uri>, mission: missionListImg) = viewModelScope.launch {
        repository.upLoadImgList(listUri,mission)
    }

}