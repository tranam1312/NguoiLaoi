package com.example.stranger.ui.home.fragment.homeFragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.stranger.R
import com.example.stranger.databinding.FragmentHomeBinding
import com.example.stranger.local.Preferences
import com.example.stranger.mode.ItemHome
import com.example.stranger.ui.home.HomeActivity
import java.util.*
import java.util.Collections.reverse
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {
    private lateinit var binding :FragmentHomeBinding
    private  val homeViewModel: HomeViewModel by lazy {
                ViewModelProvider(this).get(HomeViewModel::class.java)
    }
    private var listItem:ArrayList<ItemHome> = arrayListOf()
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var recyclerView: RecyclerView
    private val preferences : Preferences by lazy {
        Preferences.getInstance(requireContext())
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
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Stranger"
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
            val intent = Intent(requireActivity() , HomeActivity::class.java)
            intent.putExtra(FRAGMENT, NEW_POST_FRAGMENT)
            startActivity(intent)
            true
        }
        R.id.search_home->{
            val intent = Intent(requireActivity(),HomeActivity ::class.java)
            startActivity(intent)
            true
        }
        else->  super.onOptionsItemSelected(item)
    }
    fun init(){
        recyclerView = binding.recyclerviewHome
        homeAdapter = HomeAdapter(onClickLike, onClickCommnet, onClickShare,homeViewModel.getUid().uid, onClickItemImg)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter= homeAdapter
        homeViewModel.getAllData()
        homeViewModel.getAllItemHome().observe(requireActivity(), Observer {

            homeAdapter.setAdapter(it.reversed())

        })
    }
    private val onClickLike:(ItemHome) ->Unit = {
        it.listUserLike?.add(homeViewModel.getUid().uid)
        homeViewModel.updateItemHome(it.key,it)
    }
    private val onClickCommnet :(ItemHome)->Unit = {

    }
    private val onClickShare :(ItemHome)->Unit = {

    }
    private val onClickItemImg:(String)->Unit={
        Log.d("khihi","$it")
    }
    companion object{
        private val POST_CODE =200
        private val LIST_IMG = "LIST_IMG"
        private val TITLE="TITLE"
        val TAG = "HOME_FRAGMENT"
        val FRAGMENT ="FRAGMENT"
        val NEW_POST_FRAGMENT= "NEWPOST_FRAGMENT"
    }
}