package com.example.stranger.ui.searchHome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.stranger.R
import com.example.stranger.databinding.ActivitySeachHomeBinding

class SeachHomeActivity : AppCompatActivity() {
    private lateinit var  binding:ActivitySeachHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_seach_home)

    }
}