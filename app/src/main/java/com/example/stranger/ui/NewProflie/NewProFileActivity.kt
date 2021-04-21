package com.example.stranger.ui.NewProflie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stranger.R
import com.example.stranger.ui.NewProflie.fragment.NewInformationFragment

class NewProFileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_pro_file)
        supportFragmentManager.beginTransaction().replace(android.R.id.content,
            NewInformationFragment.newInstance()).commit()
    }
}