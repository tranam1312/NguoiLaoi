package com.example.stranger.local

import android.content.Context
import android.content.SharedPreferences

class Preferences (  private var sharedPreferences: SharedPreferences? = null
){

    private var mInstance: Preferences? = null


    fun updateUserId(userId: String?) {
        sharedPreferences?.edit()?.putString(USER_ID,userId )?.apply()
    }

    fun getUserId(): String? {
        return sharedPreferences?.getString(USER_ID, null)
    }
    fun updateUrlAvatar(url: String?){
        sharedPreferences?.edit()?.putString(URL_AVATAR, url)?.apply()
    }
    fun getUrlAvatar():String?{
        return sharedPreferences?.getString(URL_AVATAR,null)
    }
    fun updateUserName(userName: String?){
        sharedPreferences?.edit()?.putString(USER_NAME, userName)?.apply()
    }
    fun getUserName():String?{
        return sharedPreferences?.getString(USER_NAME, null)
    }

    fun clear() {
        sharedPreferences!!.edit().clear().apply()
        updateUserId(null)
        updateUrlAvatar(null)
        updateUserName(null)
    }

    companion object{
        private val PREFS_NAME = "share_prefs"
        private var INSTANCE : Preferences? = null
        private const val USER_ID = "USER_ID"
        private const val URL_AVATAR= "URL_AVATAR"
        private const val USER_NAME = "USER_NAME"
        fun getInstance(context:Context)= INSTANCE ?: synchronized(Preferences::class.java) {
            INSTANCE ?: Preferences(
                    context.getSharedPreferences(PREFS_NAME, 0),
                    )
                    .also { INSTANCE = it }
        }
    }
}