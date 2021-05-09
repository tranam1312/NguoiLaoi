package com.example.stranger.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.stranger.R
import com.example.stranger.local.Preferences
import com.example.stranger.ui.home.HomeActivity
import com.example.stranger.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {
    private val preferences :Preferences by lazy {
        Preferences.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({
            if (preferences.getUserId()!= null){
                val intent = Intent(this, Home::class.java)
                startActivity(intent)
                finish()
            }
            else{
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
            Log.d("kk","close")
            }
        }, 5000)

    }
}

