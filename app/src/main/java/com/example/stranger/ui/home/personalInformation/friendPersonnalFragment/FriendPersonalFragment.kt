package com.example.stranger.ui.setting.fragment.friendFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.stranger.R

class FriendFragment : Fragment() {

    companion object {
        fun newInstance() = FriendFragment()
    }
    private val viewModel: FriendViewModel by lazy {
        ViewModelProvider(this).get(FriendViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.friend_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}