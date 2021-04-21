package com.example.stranger.repository

import androidx.lifecycle.MutableLiveData
import com.example.stranger.FirebaseModel
import com.example.stranger.mission
import com.google.firebase.auth.FirebaseUser

class Repository {
    private val firebaseModel: FirebaseModel = FirebaseModel()
    fun getUserLogin():FirebaseUser = firebaseModel.getUser()
   suspend fun upLoadImg(data:ByteArray, mission: mission) = firebaseModel.upLoadAnh(data, mission)
}