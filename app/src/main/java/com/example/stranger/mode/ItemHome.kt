package com.example.stranger.model

data class ItemHome(
        var key: String = "",
        var userid: String ="",
        var userName: String = "",
        var urlAvatar: String = "",
        var content: String = "",
        var urlList: ArrayList<String> = arrayListOf(),
        var listUserLike: ArrayList<String>? = arrayListOf(),
        var datetime: String= ""
 )