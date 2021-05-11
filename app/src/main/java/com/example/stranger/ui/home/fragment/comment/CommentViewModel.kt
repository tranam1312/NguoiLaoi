package com.example.stranger.ui.home.fragment.comment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stranger.missionChange
import com.example.stranger.missionComment
import com.example.stranger.missionCommentChange
import com.example.stranger.mode.Comment
import com.example.stranger.repository.Repository
import kotlinx.coroutines.launch

class CommentViewModel : ViewModel() {

    private val repository : Repository= Repository()
    private  var  listComment : ArrayList<Comment> = arrayListOf()
    fun addComment(key:String,comment: Comment, keyComment:String){
        repository.addComment(key,comment, keyComment)
    }
    fun getKey(): String = repository.getkey()
    fun getAllComment( key: String, missionComment: missionComment){
        repository.getAllComment(key,object : missionComment{
            override fun succsess(listItemComment: ArrayList<Comment>) {
                listComment = listItemComment
                missionComment.succsess(listComment)
            }

            override fun faile() {
                TODO("Not yet implemented")
            }

        })
    }
    fun commmentChange(key: String,itemCommentChange: itemCommentChange ) {
        repository.commentChange(key,object :missionCommentChange{
                override fun add(comment: Comment) {
                    if (listComment.indexOf(comment) == -1){
                        listComment.add(comment)
                        itemCommentChange.add(listComment.size -1)
                    }
                }

                override fun change(comment: Comment) {
                    for (i in 0 until listComment.size){
                        if (listComment[i].key == comment.key){
                            listComment[i].title = comment.title
                            listComment[i].listLikeComment= comment.listLikeComment
                            listComment[i].listReplyComment = comment.listReplyComment
                            itemCommentChange.change(i)
                            break
                        }
                    }
                }
                override fun remove(comment: Comment) {
                    for (i in 0 until listComment.size){
                        if (listComment[i].key == comment.key){
                            listComment.removeAt(i)
                            itemCommentChange.remove(i)
                            break
                        }
                    }
                }

            override fun cancel(err: String) {

            }
        })
    }

}
interface itemCommentChange{
    fun add(index: Int)
    fun change(index: Int)
    fun remove(index: Int)
    fun cancle(err:String)
}