package com.example.stranger.ui.NewProflie.fragment.makeFriendFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.stranger.R
import com.example.stranger.databinding.ItemAddRequestFriendBinding
import com.example.stranger.mode.ProFile

class   MakeFriendAdapter(private val addOnClick:(ProFile)->Unit , private  var onCorpseClick:(ProFile)->Unit):RecyclerView.Adapter<MakeFriendAdapter.ViewHolder> (){
    private var listRequestFriend: ArrayList<ProFile> = arrayListOf()
    private var listAddRequset: ArrayList<ProFile> = arrayListOf()
    inner  class ViewHolder(private val addRequestFriendBinding: ItemAddRequestFriendBinding) : RecyclerView.ViewHolder(addRequestFriendBinding.root){

        fun bindRequest(proFile: ProFile){
            Glide.with(addRequestFriendBinding.root.context).load(proFile.imgUrlAvatar).into(addRequestFriendBinding.avatarRequestFriend)
            addRequestFriendBinding.nameAddRequestFriend.text = proFile.name
            addRequestFriendBinding.add.text = "Corpse"
            addRequestFriendBinding.add.setOnClickListener {
                onCorpseClick(proFile)
            }

        }
        fun bindAddRequest (proFile: ProFile){
            Glide.with(addRequestFriendBinding.root.context).load(proFile.imgUrlAvatar).into(addRequestFriendBinding.avatarRequestFriend)
            addRequestFriendBinding.nameAddRequestFriend.text = proFile.name
            addRequestFriendBinding.add.setOnClickListener {
                addOnClick(proFile)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val binding : ItemAddRequestFriendBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
          R.layout.item_add_request_friend,parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (listRequestFriend.size >0 && position >=listRequestFriend.size-1   ){
            holder.bindRequest(listRequestFriend[position])
        }
        else {
            holder.bindAddRequest(listAddRequset[position])
        }

    }

    override fun getItemCount(): Int {
      return listAddRequset.size + listRequestFriend.size
    }
    fun setRequest(listRequsestFriend: ArrayList<ProFile>){
        this.listRequestFriend = listRequestFriend
        notifyDataSetChanged()
    }
    fun setAdd(listAddRequset: ArrayList<ProFile>){
        this.listAddRequset = listAddRequset
        notifyDataSetChanged()
    }
}