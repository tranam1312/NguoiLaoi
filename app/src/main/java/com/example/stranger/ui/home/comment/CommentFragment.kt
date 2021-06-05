package com.example.stranger.ui.home.comment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.stranger.R
import com.example.stranger.databinding.BottomSelectAvatarBinding
import com.example.stranger.databinding.CommentFragmentBinding
import com.example.stranger.local.Preferences
import com.example.stranger.missionComment
import com.example.stranger.mode.Comment
import com.example.stranger.ui.home.fragment.replyComment.ReplyCommentFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import java.io.ByteArrayOutputStream

class CommentFragment : Fragment() {

    private val viewModel: CommentViewModel by lazy {
        ViewModelProvider(requireActivity()).get(CommentViewModel::class.java)
    }
    private val preferences : Preferences by lazy {
        Preferences.getInstance(requireContext())
    }
    private lateinit var bottomSelectAvatarBinding : BottomSelectAvatarBinding
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var binding: CommentFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var commentAdapter: CommentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.comment_fragment, container, false)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarComment)
        (requireActivity()as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem)= when(item.itemId){
        android.R.id.home ->{
            activity?.finish()
            true
        }
       else-> super.onOptionsItemSelected(item)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }
    private fun init(){
        recyclerView = binding.recyclerviewComment
        commentAdapter= CommentAdapter(onClickLike,onClickReply, preferences.getUserId().toString())
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = commentAdapter
        viewModel.getAllComment(preferences.getKeyItemHome().toString(),object :missionComment{
            override fun succsess(listItemComment: ArrayList<Comment>) {
                commentAdapter.setAdapter(listItemComment)
            }
            override fun faile() {
                TODO("Not yet implemented")
            }
        })
        viewModel.commmentChange(preferences.getKeyItemHome().toString(), object :
            itemCommentChange {
            override fun add(index: Int) {
                commentAdapter.notifyItemInserted(index)
                recyclerView.scrollToPosition(index)
            }

            override fun change(index: Int) {
                commentAdapter.notifyItemChanged(index,index)
            }

            override fun remove(index: Int) {
                commentAdapter.notifyItemRemoved(index  )
            }

            override fun cancle(err: String) {
                TODO("Not yet implemented")
            }

        })
        binding.postComment.setOnClickListener ( postComment)
        binding.openImg.setOnClickListener (checkPermission)
        binding.delete.setOnClickListener {
            binding.imageView.visibility = View.GONE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode ==  REQUEST_CAMERA_CODE && resultCode ==RESULT_OK){
            binding.imageView.visibility = View.VISIBLE
            binding.delete.visibility = View.VISIBLE
            var  bitmap: Bitmap = data?.extras?.get("data") as Bitmap
            binding.imageView.setImageBitmap(bitmap)
        }else if (requestCode == REQUEST_PICK_CODE && resultCode == RESULT_OK ){
            binding.imageView.visibility = View.VISIBLE
            binding.delete.visibility = View.VISIBLE
            bottomSheetDialog.dismiss()
            var uri  = data!!.data as Uri
            Glide.with(requireContext()).load(uri).into(binding.imageView)
        }
    }
    private val checkPermission = View.OnClickListener {
        TedPermission.with(context)
            .setPermissionListener(permissionListener)
            .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
            .setPermissions(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .check();
    }

    val permissionListener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            showDiaLog()
        }
        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
        }
    }

    private fun showDiaLog() {
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

    private val onClickLike:(Comment)->Unit ={
        if (it.listLikeComment?.size != null) {
            val i: Int = it.listLikeComment!!.indexOf(preferences.getUserId().toString())
            if (i > -1) {
                it.listLikeComment!!.remove(preferences.getUserId().toString())
                viewModel.updateComment(preferences.getKeyItemHome().toString(),it,it.key)
            } else if (i ==-1){
                it.listLikeComment!!.add(preferences.getUserId().toString())
                viewModel.updateComment(preferences.getKeyItemHome().toString(),it,it.key)
            }
        }else{
            it.listLikeComment!!.add(preferences.getUserId().toString())
            viewModel.updateComment(preferences.getKeyItemHome().toString(),it,it.key)
            }
    }

    private val onClickReply:(Comment)->Unit={
        preferences.updatekeyComment(it.key)
        requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in,R.anim.fade_out,R.anim.fade_in, R.anim.silde_out)
            .addToBackStack(TAG).replace(android.R.id.content,ReplyCommentFragment.newInstance()).commit()
    }
    private val postComment = View.OnClickListener{
        val keyComment: String = viewModel.getKey()
        val comment = Comment(
            keyComment, preferences.getUserId().toString(),
            binding.autoTextContainer.text.toString(), null, null, null,
            "")
        if (binding.imageView.visibility == View.VISIBLE){
            binding.imageView.isDrawingCacheEnabled = true
            binding.imageView.buildDrawingCache()
            val bitmap : Bitmap = (binding.openImg.drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()
          viewModel.upLoadImg(data,preferences.getKeyItemHome().toString(),comment.key, comment)
        } else {
            viewModel.updateComment(preferences.getKeyItemHome().toString(),comment,comment.key)
            binding.autoTextContainer.setText("")
        }
    }
    companion object {
        val TAG ="COMMENT_FRAGMENT"
        fun newInstance() = CommentFragment()
        val REQUEST_PICK_CODE = 200
        val  REQUEST_CAMERA_CODE = 120
    }
}