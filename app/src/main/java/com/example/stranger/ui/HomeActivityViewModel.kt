package com.example.stranger.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {
    private  var keyItemHomelivedata: MutableLiveData<String> = MutableLiveData()
    fun UpdateKeyItemHome(keyItemHome: String){
        keyItemHomelivedata.value = keyItemHome
        Log.d("hahhaa","$keyItemHomelivedata")
        Log.d("hahhaa","$keyItemHome")
    }
    fun getKeyItemHome(): MutableLiveData<String> {
        return keyItemHomelivedata
    }
}