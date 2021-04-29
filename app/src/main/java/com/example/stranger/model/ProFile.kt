package com.example.stranger.model


 data class ProFile( var key:String="",var name:String="",var day : String =""
 ,var month:String ="",var year :String ="", var sex:String ="",var  numberPhone : String="",
                  var   imgUrlAvatar: String ="" , var country:String ="" , var friend: ArrayList<String> = arrayListOf() )