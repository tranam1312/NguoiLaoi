package com.example.stranger.ui.NewProflie.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stranger.mode.Uid
import com.example.stranger.repository.Repository
import kotlinx.coroutines.launch
import java.security.AllPermission

class MakeFriendViewModel :ViewModel() {
    private val repository : Repository = Repository()
    fun addFendRequet(uid: Uid){
        repository.addRequestFriend(uid)
    }
    
    
}