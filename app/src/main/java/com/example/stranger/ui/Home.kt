package com.example.stranger.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.stranger.R
import com.example.stranger.databinding.ActivityHomeBinding
import com.example.stranger.ui.home.fragment.homeFragment.HomeFragment
import com.example.stranger.ui.messenger.MessengerFragment
import com.example.stranger.ui.music.MusicFragment
import com.example.stranger.ui.notifications.NotificationsFragment
import com.example.stranger.ui.setting.fragment.setting.SettingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class Home : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home)
        setSupportActionBar(binding.toolbar)
//        var bottomNavigation = binding.navView
        val navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(
                setOf(
                        R.id.navigation_home, R.id.navigation_notifications, R.id.navigation_music,R.id.navigation_messenger,R.id.navigation_setting
                )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
//        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
//        private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.navigation_home-> {
//                    supportFragment(HomeFragment())
//                    return@OnNavigationItemSelectedListener true
//                }
//                R.id.navigation_messenger -> {
//                    supportFragment(MessengerFragment())
//                    return@OnNavigationItemSelectedListener true
//                }
//                R.id.navigation_music -> {
//                    supportFragment(MusicFragment())
//                    return@OnNavigationItemSelectedListener true
//                }
//                R.id.navigation_setting ->{
//                    supportFragment(SettingFragment())
//                    return@OnNavigationItemSelectedListener  true
//                }
//                R.id.navigation_notifications ->{
//                    supportFragment(NotificationsFragment())
//                    return@OnNavigationItemSelectedListener  true
//                }
//            }
//            false
//
//        }
//    fun supportFragment(fragmnet : Fragment){
//        supportFragmentManager.beginTransaction().replace(android.R.id.content , fragmnet).commit()
//    }
}