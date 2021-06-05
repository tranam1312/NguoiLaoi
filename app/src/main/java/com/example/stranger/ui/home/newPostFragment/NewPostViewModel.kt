package com.example.stranger.ui.home.fragment.newPostFragment

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stranger.mission
import com.example.stranger.missionListImg
import com.example.stranger.mode.ItemHome
import com.example.stranger.mode.Posts
import com.example.stranger.repository.Repository
import kotlinx.coroutines.launch

class NewPostViewModel : ViewModel() {
    private val repository : Repository = Repository()
    fun addItemHome(key: String, itemHome: ItemHome, mission: mission)= viewModelScope.launch {
        var posts = Posts(getKey(),itemHome.key)
        repository.addPostsProFile(key, posts,object : mission{
            override fun succsess() {
                repository.addItemHome(itemHome, mission)
            }

            override fun failuer() {
                TODO("Not yet implemented")
            }

        })
    }
    fun getKey()= repository.getkey()
    fun upListImg(listUri: List<Uri>, mission: missionListImg) = viewModelScope.launch {
        repository.upLoadImgList(listUri,mission)
    }

}