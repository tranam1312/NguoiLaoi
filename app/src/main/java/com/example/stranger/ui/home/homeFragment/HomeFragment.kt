package com.example.stranger.ui.home.fragment.homeFragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.stranger.R
import com.example.stranger.databinding.BottomShareBinding
import com.example.stranger.databinding.FragmentHomeBinding
import com.example.stranger.local.Preferences
import com.example.stranger.mission
import com.example.stranger.missionHome
import com.example.stranger.mode.ItemHome

import com.example.stranger.ui.home.comment.CommentFragment
import com.example.stranger.ui.home.fragment.newPostFragment.NewPostFragment
import com.example.stranger.ui.home.fragment.sreachFragment.SearchFragment
import com.example.stranger.ui.home.personalInformation.PersonalInformationFragment
import com.example.stranger.ui.setting.profile.ProfileFragment
import com.google.android.material.bottomsheet.BottomSheetDialog


class HomeFragment : Fragment() {
    private lateinit var binding :FragmentHomeBinding
    private  val homeViewModel: HomeViewModel by lazy {
                ViewModelProvider(this).get(HomeViewModel::class.java)
    }
    private lateinit var bottomShareBinding: BottomShareBinding
    private lateinit var bottomSheetDialog: BottomSheetDialog
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
            requireActivity().supportFragmentManager.beginTransaction().replace(android.R.id.content,NewPostFragment.newInstance()).commit()
            true
        }
        R.id.search_home->{
            requireActivity().supportFragmentManager.beginTransaction().replace(android.R.id.content,SearchFragment.newInstance()).commit()
            true
        }
        else->  super.onOptionsItemSelected(item)
    }

    fun init(){
        recyclerView = binding.recyclerviewHome
        homeAdapter = HomeAdapter(onClickLike, onClickCommnet, onClickShare,onClickViewProFile,homeViewModel.getUid().uid
            , onClickItemImg,onClickThem)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter= homeAdapter
        homeViewModel.getAllItemHome(object : missionHome{
            override fun succsess(listItemHome: MutableList<ItemHome>) {
                homeAdapter.setAdapter(listItemHome)
            }

            override fun failure(err: String) {
                TODO("Not yet implemented")
            }

        })
        homeViewModel.itemHomeChange(object : itemHomeChange{
            override fun add(index: Int) {
                homeAdapter.notifyItemInserted(index)
            }

            override fun chang(index: Int) {
                homeAdapter.notifyItemChanged(index,index)
            }

            override fun remove(index: Int) {
                homeAdapter.notifyItemRemoved(index)
            }

            override fun cancle(err: String) {
                TODO("Not yet implemented")
            }

        })
    }
    private val onClickLike:(ItemHome) ->Unit = {
        homeViewModel.updateLikeItemHome(it)

    }

    private val onClickCommnet :(ItemHome)->Unit = {
        preferences.updateKeyItemHome(it.key)
        requireActivity().supportFragmentManager.beginTransaction().replace(android.R.id.content,
            CommentFragment.newInstance()).commit()
    }

    private val onClickShare :(ItemHome)->Unit = {
        showBottomSheetShare(it)
    }

    private val onClickItemImg:(String)->Unit={
        Log.d("khihi","$it")
    }
    private val onClickThem:(ItemHome)->Unit={

    }
    private val onClickViewProFile: (ItemHome)->Unit={
        if (it.userid == preferences.getUserId().toString()){
            requireActivity().supportFragmentManager.beginTransaction().replace(android.R.id.content,ProfileFragment.newInstance()).commit()
        }
        else if(it.userid != preferences.getUserId().toString()){
            preferences.updateUserIdPersonal(it.userid)
            requireActivity().supportFragmentManager.beginTransaction().replace(android.R.id.content,PersonalInformationFragment.newInstance()).commit()
        }
    }
    fun showBottomSheetShare(itemHome: ItemHome){
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomShareBinding = DataBindingUtil.inflate(layoutInflater, R.layout.bottom_share, null, false)
        bottomSheetDialog.setContentView(bottomShareBinding.root)
        bottomShareBinding.shareNow.setOnClickListener{
            shareNow(itemHome)
        }
        bottomShareBinding.shareNewsPost.setOnClickListener {
            shareNewPost()
        }
        bottomShareBinding.sendMessage.setOnClickListener {
            sendToMessager()
        }
        bottomSheetDialog.show()

    }
    fun bottomSheetThem(itemHome: ItemHome){

    }
    fun shareNow(itemHome: ItemHome){
        var key : String = homeViewModel.getKey()
        var itemHomeShare = ItemHome(key, preferences.getUserId().toString()
            ,"",itemHome.key,null,null,null,"")
        homeViewModel.addItemHome(key,itemHomeShare,object : mission {
            override fun succsess() {
                bottomSheetDialog.dismiss()
            }
            override fun failuer() {
                TODO("Not yet implemented")
            }

        })

    }
    fun shareNewPost(){

    }
    fun sendToMessager(){

    }

    companion object{
        fun newInstance() = HomeFragment()
        private val POST_CODE =200
        private val LIST_IMG = "LIST_IMG"
        private val TITLE="TITLE"
        val TAG = "HOME_FRAGMENT"
        val FRAGMENT ="FRAGMENT"

    }
}