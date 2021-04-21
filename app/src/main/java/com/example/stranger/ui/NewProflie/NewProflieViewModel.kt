package com.example.stranger.ui.NewProflie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stranger.FirebaseModel
import com.example.stranger.mission
import com.example.stranger.repository.Repository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class NewProflieViewModel:ViewModel() {
    private val repository: Repository = Repository()
    fun getUserLogin(): FirebaseUser =
        repository.getUserLogin()
     fun uploaimg(data: ByteArray,mission: mission) = viewModelScope.launch {
         repository.upLoadImg(data,mission)

    }
}