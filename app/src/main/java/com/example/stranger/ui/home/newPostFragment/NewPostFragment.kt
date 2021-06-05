package com.example.stranger.ui.home.fragment.newPostFragment

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stranger.R
import com.example.stranger.databinding.NewPostFragmentBinding
import com.example.stranger.local.Preferences
import com.example.stranger.mission
import com.example.stranger.missionListImg
import com.example.stranger.mode.ItemHome
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import gun0912.tedimagepicker.builder.TedImagePicker
import gun0912.tedimagepicker.builder.type.MediaType
import gun0912.tedimagepicker.util.ToastUtil

class NewPostFragment : Fragment() {

    companion object {
        fun newInstance() = NewPostFragment()
        val TAG= "NEWPOST_FRAGMENT"
    }

    private val preferences: Preferences by lazy {
        Preferences.getInstance(ToastUtil.context)
    }
    private lateinit var key :String
    private var uriList: ArrayList<Uri> = arrayListOf()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ImgURIAdapter
    private val viewModel: NewPostViewModel by lazy {
        ViewModelProvider(requireActivity()).get(NewPostViewModel::class.java)
    }
    private lateinit var binding : NewPostFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.new_post_fragment,container, false)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarNewpost)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        intiview()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.new_post_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem)= when(item.itemId){
        android.R.id.home -> {
            requireActivity().finish()
            true
        }
        R.id.post->{
            post()
            true
        }

        else-> super.onOptionsItemSelected(item)
    }
    private fun intiview(){
        getAllPoto()
        adapter = ImgURIAdapter(delete)
        recyclerView = binding.recyclerviewImgNewpost
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerView.adapter = adapter
    }
    private  fun getAllPoto(){
        binding.selectImg.setOnClickListener {
            TedPermission.with(requireContext())
                    .setPermissionListener(permissionlistener)
                    .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                    .setPermissions(android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE )
                    .check();
        }
    }

    var delete :(Uri) ->Unit={
        uriList.remove(it)
        adapter.setAdater(uriList)
    }

    val permissionlistener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            openSelectImg()
        }
        override fun onPermissionDenied(deniedPermissions: List<String>) {
            Toast.makeText(requireContext(), "Permission Denied\n$deniedPermissions", Toast.LENGTH_SHORT).show()
        }
    }
    private fun post() {
        key = viewModel.getKey()
        if (uriList.size ==0){
            var itemHome = ItemHome(key, preferences.getUserId().toString()
                ,binding.editText.text.toString(),"",null,null,null,"")
            viewModel.addItemHome(preferences.getUserId().toString(),itemHome,object : mission {
                override fun succsess() {
                    requireActivity().finish()
                }
                override fun failuer() {
                    TODO("Not yet implemented")
                }

            })
        }else{
        viewModel.upListImg(uriList , object : missionListImg {
            override fun success(urlList: ArrayList<String>) {
                postFirebase(urlList)
            }

            override fun failure(exception: Exception) {
                TODO("Not yet implemented")
            }

            override fun load() {
                TODO("Not yet implemented")
            }

        })
    }
    }

    fun openSelectImg() {
            TedImagePicker.with(requireContext())
                    .image()
                    .mediaType(MediaType.IMAGE)
                    .max(6, "Please do not choose")
                    .startMultiImage { uriList -> showMultiImage(uriList) }
    }
    private fun showMultiImage(uriList: List<Uri>) {
            this.uriList = uriList as ArrayList<Uri>
            adapter.setAdater(this.uriList)
            Log.d("anh", "$uriList")
        }

    private fun postFirebase(urlList: ArrayList<String>){
        var itemHome = ItemHome(key, preferences.getUserId().toString()
                ,binding.editText.text.toString(),null,urlList,null,null,"")
        viewModel.addItemHome(preferences.getUserId().toString(),itemHome,object : mission {
            override fun succsess() {
                requireActivity().finish()
            }
            override fun failuer() {
                TODO("Not yet implemented")
            }

        })

    }


}