package com.example.stranger.ui.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stranger.R
import com.example.stranger.databinding.FragmentHomeBinding
import com.example.stranger.model.ItemHome
import com.example.stranger.ui.newpost.NewPostActivity
import com.example.stranger.ui.searchHome.SeachHomeActivity
import kotlin.collections.ArrayList as ArrayList1


class HomeFragment : Fragment() {

    private lateinit var binding :FragmentHomeBinding
    private  val homeViewModel: HomeViewModel by lazy {
                ViewModelProvider(this).get(HomeViewModel::class.java)
    }
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var recyclerView: RecyclerView

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
        init()
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.home_menu, menu)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean  = when(item.itemId){
        R.id.newpost ->{
            val intent = Intent(requireActivity(), NewPostActivity::class.java)
            startActivityForResult(intent, POST_CODE)
            true
        }
        R.id.search_home->{
            val intent = Intent(requireActivity(), SeachHomeActivity::class.java)
            startActivity(intent)
            true
        }
        else->  super.onOptionsItemSelected(item)
    }
    fun init(){
        recyclerView = binding.recyclerviewHome
        homeAdapter = HomeAdapter(onClickLike, onClickCommnet, onClickShare,homeViewModel.getUid().uid)
        homeAdapter.setAdapter(homeViewModel.getAllHome())
        Log.d("kkkkkk","${homeViewModel.getAllHome()}")
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter= homeAdapter

    }
    private val onClickLike:(ItemHome) ->Unit = {
        it.listUserLike?.add(homeViewModel.getUid().uid)
        homeViewModel.updateItemHome(it.key,it)
    }
    private val onClickCommnet :(ItemHome)->Unit = {

    }
    private val onClickShare :(ItemHome)->Unit = {

    }
    companion object{
        private val POST_CODE =200
        private val LIST_IMG = "LIST_IMG"
        private val TITLE="TITLE"
    }
}