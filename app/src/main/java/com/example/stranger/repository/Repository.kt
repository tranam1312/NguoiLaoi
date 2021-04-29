package com.example.stranger.repository

import android.net.Uri
import com.example.stranger.*
import com.example.stranger.model.ItemHome
import com.example.stranger.model.ProFile
import com.google.firebase.auth.FirebaseUser

class Repository {
    private val firebaseModel: FirebaseModel = FirebaseModel()
    fun getUserLogin():FirebaseUser = firebaseModel.getUser()
    fun upLoadImg(data:ByteArray,key:String, mission: missionImg) = firebaseModel.upLoadAnh(data, key, mission)
    fun getkey():String =firebaseModel.getKey()
    fun newProFile(user: ProFile, mission: mission) = firebaseModel.newFrofile(user,mission)
    fun upLoadImgList(dataList:ArrayList<Uri>, missionListImg: missionListImg) = firebaseModel.upLoadListImg(dataList, missionListImg)
    fun upDateItemHome(key: String,itemHome: ItemHome) = firebaseModel.updateItemHome(key, itemHome)
    fun addItemHome(key: String, itemHome:ItemHome, mission: mission)= firebaseModel.addItemHome(key, itemHome,mission)
    fun getAllHome():ArrayList<ItemHome> = firebaseModel.getAllHome()
}