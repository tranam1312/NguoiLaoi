package com.example.stranger.ui.NewProflie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stranger.mission
import com.example.stranger.missionImg
import com.example.stranger.model.ProFile
import com.example.stranger.repository.Repository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class NewProflieViewModel:ViewModel() {
    private val repository: Repository = Repository()
    fun getUserLogin(): FirebaseUser =
        repository.getUserLogin()
     fun uploaimg(data: ByteArray ,key :String,mission: missionImg) = viewModelScope.launch {
         repository.upLoadImg(data, key,mission)
    }
    fun getkey():String = repository.getkey()
    fun newProfile(user: ProFile, mission: mission)=viewModelScope.launch {
        repository.newProFile(user,mission)
    }
}