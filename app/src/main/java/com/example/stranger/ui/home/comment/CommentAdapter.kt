package com.example.stranger.ui.home.comment

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.stranger.R
import com.example.stranger.databinding.ItemCommentBinding
import com.example.stranger.missionProFile
import com.example.stranger.mode.Comment
import com.example.stranger.mode.ProFile
import com.example.stranger.repository.Repository

class CommentAdapter(private val onClickLike:(Comment)->Unit,private val onClickReplyComment :(Comment) ->Unit,private val  userId : String): RecyclerView.Adapter<CommentAdapter.ViewHolder>(){
    private var listComment :ArrayList<Comment> =  arrayListOf()
    private val repository: Repository = Repository()
    inner  class  ViewHolder(private val itemCommentBinding: ItemCommentBinding) : RecyclerView.ViewHolder(itemCommentBinding.root){
        fun bind(comment: Comment) {
            repository.getProFile(comment.userId, object : missionProFile {
                override fun succsess(proFile: ProFile) {
                 itemCommentBinding.viewComment.nameComment.text= proFile.name
                    Glide.with(itemCommentBinding.root).load(proFile.imgUrlAvatar).diskCacheStrategy(
                        DiskCacheStrategy.ALL).into(itemCommentBinding.viewComment.imageAvatar)
                }
            })
            itemCommentBinding.viewComment. title.text = comment.title
            if (comment.listLikeComment?.size !=0){
                itemCommentBinding.viewComment.numberLike.visibility = View.VISIBLE
                var j: Int = comment.listLikeComment!!.indexOf(userId)
                if (j == -1) {
                    itemCommentBinding.viewComment.like.setTextColor(Color.parseColor("#C0C0C0"))
                    itemCommentBinding.viewComment.numberLike.text = comment.listLikeComment!!.size.toString()
                } else if (j > -1) {
                    itemCommentBinding.viewComment.numberLike.text = comment.listLikeComment!!.size.toString()
                    itemCommentBinding.viewComment.like.setTextColor(Color.parseColor("#FF1493"))
                }
            } else if (comment.listLikeComment?.size ==0){
                itemCommentBinding.viewComment.like.setTextColor(Color.parseColor("#C0C0C0"))
                itemCommentBinding.viewComment.numberLike.visibility = View.GONE
            }
            itemCommentBinding.viewComment.like.setOnClickListener {
                if (comment.listLikeComment?.size !=0){
                    var j: Int = comment.listLikeComment!!.indexOf(userId)
                    if (j == -1) {
                        itemCommentBinding.viewComment.like.setTextColor(Color.parseColor("#FF1493"))
                    } else if (j > -1) {
                        itemCommentBinding.viewComment.like.setTextColor(Color.parseColor("#C0C0C0"))
                    }
                }else if (comment.listLikeComment?.size ==0){
                    itemCommentBinding.viewComment.like.setTextColor(Color.parseColor("#FF1493"))
                }
                onClickLike(comment)
            }
            itemCommentBinding.viewComment.reply.setOnClickListener {
                onClickReplyComment(comment)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val  itemCommentBinding : ItemCommentBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_comment, parent, false)
        return ViewHolder(itemCommentBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
     holder.bind(listComment[position])
    }

    override fun getItemCount(): Int {
        return listComment.size
    }

    fun setAdapter(listComment: ArrayList<Comment>){
        this.listComment = listComment
        Log.d("tag", "${this.listComment}")
        Log.d("tag", "$listComment")
        notifyDataSetChanged()
    }

}