package com.example.stranger.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.stranger.R
import com.example.stranger.ui.NewProflie.fragment.makeFriendFragment.KetbanFragment
import com.example.stranger.ui.home.HomeActivity

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        var fragment: String = intent.getStringExtra(HomeActivity.FRAGMENT) as String
        if (fragment !=null){
            supportFragment(KetbanFragment.newInstance())
        }
    }
    fun supportFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(android.R.id.content,fragment).commit()
    }
}