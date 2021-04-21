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
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import gun0912.tedimagepicker.builder.TedImagePicker
import gun0912.tedimagepicker.builder.type.MediaType


class NewPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewPostBinding
    private val viewModel : NewPostViewModel by lazy {
        ViewModelProvider(this).get(NewPostViewModel::class.java)
    }
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

        else-> super.onOptionsItemSelected(item)
    }
    private fun intiview(){
        getPoto()
        adapter = ImgURIAdapter(delete)
        recyclerView = binding.recyclerviewImgNewpost
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = adapter
    }

    private  fun getPoto(){
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


}