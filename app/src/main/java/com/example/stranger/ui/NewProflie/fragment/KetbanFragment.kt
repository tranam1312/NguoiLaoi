package com.example.stranger.ui.NewProflie.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.stranger.R
import com.example.stranger.databinding.FragmentKetbanBinding

class KetbanFragment : Fragment() {

    private lateinit var binding:FragmentKetbanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_ketban,container,false)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarKetban)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    companion object {
        fun newInstance()= KetbanFragment()
        const val TAG = "KET_BAN_FRAGMENT"
    }
}