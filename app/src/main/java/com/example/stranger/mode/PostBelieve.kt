package com.example.stranger.model

import android.icu.text.CaseMap

data class PostBelieve( var key:String="", var userId:String="",var title: String="",var listUrlImg: ArrayList<String> = arrayListOf(),
var listLike : ArrayList<String> = arrayListOf())