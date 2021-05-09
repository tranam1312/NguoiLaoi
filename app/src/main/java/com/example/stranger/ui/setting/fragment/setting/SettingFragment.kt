package com.example.stranger.ui.setting.fragment.setting

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.stranger.R
import com.example.stranger.databinding.SettingFragmentBinding
import com.example.stranger.ui.login.LoginActivity
import com.example.stranger.ui.setting.SettingActivity
import com.google.firebase.auth.FirebaseAuth

class SettingFragment : Fragment() {
    private var firebaseAuth : FirebaseAuth = FirebaseAuth.getInstance()
    private val preferences : com.example.stranger.local.Preferences by lazy {
        com.example.stranger.local.Preferences.getInstance(requireContext())

    }


    private lateinit var binding:SettingFragmentBinding
    private lateinit var viewModel: SettingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.setting_fragment,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.logout.setOnClickListener {
            firebaseAuth.signOut()
            preferences.clear()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }
        binding.friend.setOnClickListener {
            val  intent = Intent(requireContext(), SettingActivity::class.java)
            intent.putExtra(FRAGMENT, MAKE_FRIEND)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }
    companion object {
        fun newInstance() = SettingFragment()
        val TAG ="SETTING_FRAGMENT"
        val FRAGMENT = "FRAGMENT"
        val MAKE_FRIEND = "MAKE_FRIEND"
    }




}