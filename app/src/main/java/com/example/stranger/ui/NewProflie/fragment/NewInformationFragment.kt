package com.example.stranger.ui.NewProflie.fragment

import android.R.attr.data
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.stranger.FirebaseModel
import com.example.stranger.R
import com.example.stranger.databinding.BottomSelectAvatarBinding
import com.example.stranger.databinding.FragmentNewInformationBinding
import com.example.stranger.mission
import com.example.stranger.ui.NewProflie.NewProflieViewModel
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.storage.UploadTask
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import java.io.ByteArrayOutputStream
import java.io.File
import java.lang.Exception


class NewInformationFragment : Fragment() {
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var bottomSelectAvatarBinding : BottomSelectAvatarBinding
    private lateinit var binding: FragmentNewInformationBinding
    private val viewmodel:NewProflieViewModel by lazy {
        ViewModelProvider(this).get(NewProflieViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_new_information,
            container,
            false
        )
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarInf)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Glide.with(binding.root.context).load(viewmodel.getUserLogin().photoUrl).into(binding.imgAvtar)
        binding.name.setText(viewmodel.getUserLogin().displayName)
        binding.email.setText(viewmodel.getUserLogin().email)
        binding.numberPhone.setText(viewmodel.getUserLogin().phoneNumber)
        binding.imgAvtar.setOnClickListener {
            checkPermission()
        }

    }
    private fun checkPermission(){
        TedPermission.with(context)
            .setPermissionListener(permissionListener)
            .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
            .setPermissions(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .check();
    }
    val permissionListener:PermissionListener = object : PermissionListener{
        override fun onPermissionGranted() {
            showDiaLog()
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {

        }

    }
    private fun showDiaLog(){
        bottomSheetDialog = BottomSheetDialog(requireActivity())
        bottomSelectAvatarBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.bottom_select_avatar,
            null,
            false
        )
        bottomSheetDialog.setContentView(bottomSelectAvatarBinding.root)
        bottomSelectAvatarBinding.camera.setOnClickListener {
           val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_CAMERA_CODE)
        }
        bottomSelectAvatarBinding.library.setOnClickListener {
             val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_PICK_CODE)
        }
        bottomSheetDialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CAMERA_CODE && resultCode == RESULT_OK && data != null) {
            bottomSheetDialog.dismiss()
            val imageBitmap = data.extras?.get("data") as Bitmap
            binding.imgAvtar.setImageBitmap(imageBitmap)
        }
        else if (requestCode == REQUEST_PICK_CODE && resultCode== RESULT_OK && data != null){
            bottomSheetDialog.dismiss()
            Log.d("contexxt","${data.data}")
           val imgUri = data.data as Uri
            Log.d("contexxt","${imgUri}")
            Glide.with(binding.root.context).load(imgUri).into(binding.imgAvtar)
        }

    }
    fun postProFile(){
        binding.imgAvtar.isDrawingCacheEnabled = true
        binding.imgAvtar.buildDrawingCache()
        val bitmap = (binding.imgAvtar.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        viewmodel.uploaimg(data, object :mission{
            override fun success(task: UploadTask.TaskSnapshot) {

            }

            override fun failure(exception: Exception) {

            }

            override fun urlDowload(task: Task<UploadTask.TaskSnapshot>) {

            }

            override fun load(uploadTask: UploadTask.TaskSnapshot) {

            }


        })
    }

    override fun onOptionsItemSelected(item: MenuItem)=when(item.itemId){
        android.R.id.home->{
            requireActivity().finish()
            true
        }
        else-> super.onOptionsItemSelected(item)
    }


    companion object {
        fun newInstance()= NewInformationFragment()
        val REQUEST_CAMERA_CODE= 200
        val REQUEST_PICK_CODE =120
    }
}