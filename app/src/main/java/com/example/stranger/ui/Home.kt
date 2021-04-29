package com.example.stranger.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.stranger.R
import com.example.stranger.databinding.ActivityHomeBinding
import com.example.stranger.ui.home.HomeFragment
import com.example.stranger.ui.messenger.MessengerFragment
import com.example.stranger.ui.music.MusicFragment
import com.example.stranger.ui.notifications.NotificationsFragment
import com.example.stranger.ui.setting.SettingFragment

class Home : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
    private val homeFragment = HomeFragment()
    private val messengerFragment= MessengerFragment()
    private val musicFragment = MusicFragment()
    private val notificationsFragment = NotificationsFragment()
    private val  settingFragment = SettingFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home)
        setSupportActionBar(binding.toolbar)
        binding.navView.setOnNavigationItemSelectedListener  {
            when(it.itemId){
                R.id.navigation_home-> {
                    supportActionBar?.title="Stranger"
                    startFragment(homeFragment)
                }
                R.id.navigation_messenger -> {
                    supportActionBar?.title="Messenger"
                    startFragment(messengerFragment)
                }
                R.id.navigation_notifications -> {
                    supportActionBar?.title="Notification"
                    startFragment(notificationsFragment)
                }
                R.id.navigation_music -> {
                    supportActionBar?.title="Music"
                    startFragment(musicFragment)
                }
                R.id.navigation_setting -> {
                    supportActionBar?.title="Setting"
                    startFragment(settingFragment)
                }
            }
            true
        }
    }
    private fun startFragment(fragment:Fragment){
        supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in,R.anim.fade_out,R.anim.fade_in,R.anim.silde_out).replace(android.R.id.content,fragment)
                .commit()
    }
}