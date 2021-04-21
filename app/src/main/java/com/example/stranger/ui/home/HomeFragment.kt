package com.example.stranger.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.stranger.R
import com.example.stranger.databinding.FragmentHomeBinding
import com.example.stranger.ui.newpost.NewPostActivity
import com.example.stranger.ui.searchHome.SeachHomeActivity


class HomeFragment : Fragment() {

    private lateinit var binding :FragmentHomeBinding
    private  val homeViewModel: HomeViewModel by lazy {
                ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.home_menu, menu)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean  = when(item.itemId){
        R.id.newpost ->{
            val intent = Intent(requireActivity(), NewPostActivity::class.java)
            startActivity(intent)
            true
        }
        R.id.search_home->{
            val intent = Intent(requireActivity(), SeachHomeActivity::class.java)
            startActivity(intent)
            true
        }
        else->  super.onOptionsItemSelected(item)
    }
}