package com.example.stranger.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stranger.model.ItemHome
import com.example.stranger.repository.Repository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private var  repository: Repository  = Repository()
    fun updateItemHome(key:String,itemHome: ItemHome) = viewModelScope.launch {
        repository.upDateItemHome(key, itemHome)
    }
    fun getUid()= repository.getUserLogin()
    fun getAllHome(): ArrayList<ItemHome> = repository.getAllHome()
}