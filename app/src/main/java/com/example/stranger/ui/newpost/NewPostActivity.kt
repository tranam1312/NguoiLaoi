package com.example.stranger.ui.newpost

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stranger.R
import com.example.stranger.databinding.ActivityNewPostBinding
import com.example.stranger.local.Preferences
import com.example.stranger.mission
import com.example.stranger.missionListImg
import com.example.stranger.model.Img
import com.example.stranger.model.ItemHome
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import gun0912.tedimagepicker.builder.TedImagePicker
import gun0912.tedimagepicker.builder.type.MediaType
import gun0912.tedimagepicker.util.ToastUtil.context

class NewPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewPostBinding
    private val viewModel : NewPostViewModel by lazy {
        ViewModelProvider(this).get(NewPostViewModel::class.java)
    }
    private val preferences: Preferences by lazy {
        Preferences.getInstance(context)
    }
    private lateinit var key :String
    private var uriList: ArrayList<Uri> = arrayListOf()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ImgURIAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_post)
        setSupportActionBar(binding.toolbarNewpost)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        intiview()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val  inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.new_post_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem)= when(item.itemId){
        android.R.id.home -> {
            finish()
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
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = adapter
    }
    private fun post() {
        key = viewModel.getKey()
        viewModel.upListImg(uriList, object : missionListImg {
            override fun success(urlList: ArrayList<String>, listKey: ArrayList<String>) {
                postFirebase(urlList, listKey)
            }

            override fun failure(exception: Exception) {
                TODO("Not yet implemented")
            }

            override fun load() {
                TODO("Not yet implemented")
            }

        })
    }
    private fun postFirebase(urlList: ArrayList<String>, listKey:ArrayList<String>){
//        var imgList : ArrayList<Img> = arrayListOf()
//        var j:Int=0
//        for (i in listKey){
//            var img: Img = Img(i, urlList[j])
//            imgList.add(img)
//            j++
//        }
        var itemHome: ItemHome = ItemHome(key, preferences.getUserId().toString(),preferences.getUserName().toString(),preferences.getUrlAvatar().toString()
                ,binding.editText.text.toString(),urlList,null,"")
        viewModel.addItemHome(key,itemHome,object :mission{
            override fun succsess() {
                finish()
            }

            override fun failuer() {
                TODO("Not yet implemented")
            }

        })

    }

    private  fun getAllPoto(){
        binding.selectImg.setOnClickListener {
            TedPermission.with(this)
                    .setPermissionListener(permissionlistener)
                    .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                    .setPermissions(android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE )
                    .check();
        }
    }

    val permissionlistener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            openSelectImg()
        }
        override fun onPermissionDenied(deniedPermissions: List<String>) {
            Toast.makeText(applicationContext, "Permission Denied\n$deniedPermissions", Toast.LENGTH_SHORT).show()
        }
    }
    fun openSelectImg() {
        TedImagePicker.with(this)
                .image()
                .mediaType(MediaType.IMAGE)
                .max(6, "Please do not choose")
                .startMultiImage { uriList -> showMultiImage(uriList) }
    }

    private fun showMultiImage(uriList: List<Uri>) {
        this.uriList = uriList as ArrayList<Uri>
        adapter.setAdater(this.uriList)
    }

    var delete :(Uri) ->Unit={
        uriList.remove(it)
        adapter.setAdater(uriList)
    }

    companion object{
        private val LIST_IMG = "LIST_IMG"
        private val TITLE="TITLE"
    }
}