package com.example.stranger.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.example.stranger.R
import com.example.stranger.databinding.ActivityHomeBinding
import com.example.stranger.ui.ViewPagerAdapter
import com.example.stranger.ui.home.fragment.homeFragment.HomeFragment
import com.example.stranger.ui.messenger.MessengerFragment
import com.example.stranger.ui.music.MusicFragment
import com.example.stranger.ui.notifications.NotificationsFragment
import com.example.stranger.ui.setting.setting.SettingFragment
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.tabs.TabLayout

class Home : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var badgeDrawable: BadgeDrawable
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewPager = binding.viewPager
        tabLayout = binding.tabsLayout
        viewPager.offscreenPageLimit = 5
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager,tabLayout.tabCount)
        viewPagerAdapter.addFragment(HomeFragment.newInstance())
        viewPagerAdapter.addFragment(MusicFragment.newInstance())
        viewPagerAdapter.addFragment(MessengerFragment.newInstance())
        viewPagerAdapter.addFragment(NotificationsFragment.newInstance())
        viewPagerAdapter.addFragment(SettingFragment.newInstance())
        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_home)
        tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_music)
        tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_message_circle)
        tabLayout.getTabAt(3)?.setIcon(R.drawable.ic_notifications_black_24dp)
        tabLayout.getTabAt(4)?.setIcon(R.drawable.ic_settings)
        badgeDrawable = tabLayout.getTabAt(0)!!.orCreateBadge
        badgeDrawable.isVisible = true
    }
}


