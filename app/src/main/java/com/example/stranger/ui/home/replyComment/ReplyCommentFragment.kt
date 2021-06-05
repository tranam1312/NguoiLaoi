package com.example.stranger.ui.home.fragment.replyComment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.stranger.R
import com.example.stranger.databinding.BottomSelectAvatarBinding
import com.example.stranger.databinding.ReplyCommentFragmentBinding
import com.example.stranger.local.Preferences
import com.example.stranger.mode.Comment
import com.example.stranger.mode.ReplyComment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission

class  ReplyCommentFragment : Fragment() {

    private  val viewModel: ReplyCommentViewModel by lazy {
        ViewModelProvider(requireActivity()).get(ReplyCommentViewModel::class.java)
    }
    private val preferences : Preferences by lazy {
        Preferences.getInstance(requireContext())
    }
    private lateinit var bottomSelectAvatarBinding: BottomSelectAvatarBinding
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var  replyCommentAdapter: ReplyCommentAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding : ReplyCommentFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.reply_comment_fragment, container, false)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarReplyComment)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }
    fun init(){
        recyclerView = binding.recyclerviewReplyComment
        recyclerView.setHasFixedSize(true)
        replyCommentAdapter = ReplyCommentAdapter(onClickLikeComment, onClickCommnet,onClickLikeReplyComment, onClickReplyComment, preferences.getUserId().toString())
        recyclerView.adapter = replyCommentAdapter
        viewModel.getItemComment(preferences.getKeyItemHome().toString(), preferences.getKeyComment().toString(),object : missionReply{
            override fun success(comment: Comment, listReplyComment: ArrayList<ReplyComment>) {
                var litsComment : ArrayList<Comment> = arrayListOf()
                litsComment.add(comment)
                replyCommentAdapter.setAdapter(litsComment, listReplyComment)
            }

        })
        viewModel.replyCommentChange(preferences.getKeyItemHome().toString(),preferences.getKeyComment().toString(),object :replyCommentChange{
            override fun add(index: Int) {
                replyCommentAdapter.notifyItemInserted(index)
                recyclerView.scrollToPosition(index)
            }

            override fun change(index: Int) {
                replyCommentAdapter.notifyItemChanged(index,index)

            }

            override fun remove(index: Int) {
                replyCommentAdapter.notifyItemRemoved(index)
            }

            override fun cancel(err: String) {
                TODO("Not yet implemented")
            }

        })
        binding.delete.setOnClickListener {
            binding.imageView.visibility = View.GONE
        }
        binding.openImg.setOnClickListener {
            checkPermission()

        }
        binding.postReplyComment.setOnClickListener {
            postReplyComment()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CAMERA_CODE && resultCode == RESULT_OK){
            val bitmap: Bitmap = data?.extras?.get("data") as Bitmap
            binding.imageView.visibility= View.VISIBLE
            binding.imageView.setImageBitmap(bitmap)
        } else if (requestCode == REQUEST_PICK_CODE && resultCode == RESULT_OK ){
            var uri  = data?.data
            binding.imageView.visibility = View.VISIBLE
            Glide.with(requireContext()).load(uri).into(binding.imageView)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem)= when(item.itemId) {
        android.R.id.home ->{
            requireActivity().supportFragmentManager.popBackStack()
            true
        }
        else-> super.onOptionsItemSelected(item)
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
    private  val permissionListener: PermissionListener = object : PermissionListener{
        override fun onPermissionGranted() {
            showDialog()
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            TODO("Not yet implemented")
        }

    }
    private fun showDialog(){
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSelectAvatarBinding = DataBindingUtil.inflate(layoutInflater, R.layout.bottom_select_avatar, null, false)
        bottomSelectAvatarBinding.camera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE )
            startActivityForResult(intent, REQUEST_CAMERA_CODE)
        }
        bottomSelectAvatarBinding.library.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_PICK_CODE)
        }

    }
    private fun postReplyComment(){
        var replyComment = ReplyComment(viewModel.getKey(),preferences.getUserId().toString(),binding.autoTextContainer.text.toString()
        ,null,null,"" )
        viewModel.addReplyComment(preferences.getKeyItemHome().toString(), preferences.getKeyComment().toString(), replyComment.key, replyComment)
        binding.autoTextContainer.setText("")
    }
    private val onClickLikeComment :(Comment) ->Unit={
        if (it.listLikeComment?.size != null) {
            val i: Int = it.listLikeComment!!.indexOf(preferences.getUserId().toString())
            if (i > -1) {
                it.listLikeComment!!.remove(preferences.getUserId().toString())
                viewModel.addComment(preferences.getKeyItemHome().toString(),it,it.key)
            } else if (i ==-1){
                it.listLikeComment!!.add(preferences.getUserId().toString())
                viewModel.addComment(preferences.getKeyItemHome().toString(),it,it.key)
            }
        }else{
            it.listLikeComment!!.add(preferences.getUserId().toString())
            viewModel.addComment(preferences.getKeyItemHome().toString(),it,it.key)
        }
    }

    private val  onClickCommnet:(Comment)->Unit={

    }
    private val onClickLikeReplyComment:(ReplyComment)->Unit ={
        if (it.listLikeReplyComment?.size != null) {
            val i: Int = it.listLikeReplyComment!!.indexOf(preferences.getUserId().toString())
            if (i > -1) {
                it.listLikeReplyComment!!.remove(preferences.getUserId().toString())
                viewModel.updateReplyComment(preferences.getKeyItemHome().toString(),preferences.getKeyComment().toString(), it.key, it)
            } else if (i ==-1){
                it.listLikeReplyComment!!.add(preferences.getUserId().toString())
                viewModel.updateReplyComment(preferences.getKeyItemHome().toString(),preferences.getKeyComment().toString(), it.key, it)
            }
        }else{
            it.listLikeReplyComment!!.add(preferences.getUserId().toString())
            viewModel.updateReplyComment(preferences.getKeyItemHome().toString(),preferences.getKeyComment().toString(), it.key, it)
        }
    }
    private val onClickReplyComment: (ReplyComment)->Unit ={

    }

    companion object {
        fun newInstance() = ReplyCommentFragment()
        val REQUEST_PICK_CODE = 200
        val  REQUEST_CAMERA_CODE = 120
    }



}