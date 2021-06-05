package com.example.stranger.ui.home.fragment.replyComment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stranger.missionReplyCommentChange
import com.example.stranger.missionReplyCommment
import com.example.stranger.mode.Comment
import com.example.stranger.mode.ReplyComment
import com.example.stranger.msissionGetItemComment
import com.example.stranger.repository.Repository
import kotlinx.coroutines.launch


class ReplyCommentViewModel : ViewModel() {
    private val repository: Repository = Repository()
    private var arrayListReplyComment: ArrayList<ReplyComment> = arrayListOf()

    fun getKey() = repository.getkey()
    fun addReplyComment(key:String, keyComment:String, keyReplyComment: String, replyComment: ReplyComment)= viewModelScope.launch{
        repository.updateReplyComment(key,keyComment, keyReplyComment, replyComment)
    }
    fun addComment(key:String,comment: Comment, keyComment:String){
        repository.updateComment(key,comment, keyComment)
    }
    fun updateReplyComment(key: String, keyComment: String, keyReplyComment: String,replyComment: ReplyComment)= viewModelScope.launch {
        repository.updateReplyComment(key, keyComment, keyReplyComment, replyComment)
    }
    fun getItemComment(key: String , keyComment: String,missionReply: missionReply )= viewModelScope.launch{
        repository.getItemComment(key, keyComment, object : msissionGetItemComment{
            override fun succsess(comment: Comment) {
                repository.getAllReplyComment(key,keyComment,object : missionReplyCommment{
                    override fun succses(listReplyComment: ArrayList<ReplyComment>) {
                        arrayListReplyComment= listReplyComment
                        missionReply.success(comment, arrayListReplyComment)
                    }
                    override fun failure(err: String) {
                    }

                })
            }
            override fun failure(err: String) {
            }
        })

        }
    fun replyCommentChange(key: String, keyComment: String,replyCommentChange: replyCommentChange)= viewModelScope.launch{
        repository.replyCommentChange(key, keyComment, object : missionReplyCommentChange{
            override fun add(replyComment: ReplyComment) {
                 if (arrayListReplyComment.indexOf(replyComment) == -1){
                     arrayListReplyComment.add(replyComment)
                     replyCommentChange.add(arrayListReplyComment.size)
                 }
            }

            override fun change(replyComment: ReplyComment) {
                for (i in 0 until arrayListReplyComment.size){
                    if (arrayListReplyComment[i].key == replyComment.key){
                        arrayListReplyComment[i].container = replyComment.container
                        arrayListReplyComment[i].listLikeReplyComment= replyComment.listLikeReplyComment
                        arrayListReplyComment[i].urlImg = replyComment.urlImg
                        replyCommentChange.change(i)
                        break
                    }
            }
            }

            override fun remove(replyComment: ReplyComment) {
                for (i in 0 until arrayListReplyComment.size){
                    if (arrayListReplyComment[i].key == replyComment.key){
                        arrayListReplyComment.removeAt(i)
                        replyCommentChange.remove(i)
                        break
                    }
                }
            }

            override fun cancel(err: String) {
                TODO("Not yet implemented")
            }

        })
    }

}

interface missionReply{
    fun success(comment: Comment, listReplyComment: ArrayList<ReplyComment>)
}
interface replyCommentChange{
    fun add(index: Int)
    fun change(index: Int)
    fun remove(index: Int)
    fun cancel(err: String)
}


