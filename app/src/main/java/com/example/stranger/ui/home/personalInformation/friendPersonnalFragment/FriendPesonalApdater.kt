package com.example.stranger.ui.setting.fragment.friendFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.stranger.R
import com.example.stranger.databinding.ItemFriendBinding
import com.example.stranger.databinding.ItemHeaderFriendBinding
import com.example.stranger.missionProFile
import com.example.stranger.mode.ProFile
import com.example.stranger.repository.Repository

class FiendApdater (private val uid: String): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val repository = Repository()
    private var listFriend: ArrayList<String> = arrayListOf()
    inner class ViewHoler(private val itemFriendBinding: ItemFriendBinding): RecyclerView.ViewHolder(itemFriendBinding.root){
        fun bind(uidFriend: String){
            repository.getProFile(uidFriend,object : missionProFile {
                override fun succsess(proFile: ProFile) {
                    val proFileFriend: ProFile  = proFile
                    Glide.with(itemFriendBinding.root).load(proFileFriend.imgUrlAvatar).diskCacheStrategy(
                        DiskCacheStrategy.AUTOMATIC).into(itemFriendBinding.avatarFriend
                    )
                    itemFriendBinding.nameFriend.text = proFileFriend.name
                    repository.getProFile(uid, object : missionProFile{
                        override fun succsess(proFile: ProFile) {
                            var i : Int =0
                            for (item in proFileFriend.listFriend.values){
                                if (proFile.friendRequest.values.indexOf(item)!= -1){
                                    i ++
                                }
                            }
                            if (i >0){
                                itemFriendBinding.mutualFriends.text = "$i bạn chung"
                            } else{
                                itemFriendBinding.mutualFriends.visibility = View.GONE
                            }
                        }
                    })
                }
            })
        }
    }
    inner class Header(private val itemHeader: ItemHeaderFriendBinding ): RecyclerView.ViewHolder(itemHeader.root){
        fun bind(){
            itemHeader.headerTitle.text = "${listFriend.size} người bạn "
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEADER){
            val binding: ItemHeaderFriendBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_header_friend, parent,false)
            return Header(binding)
        }
        val binding : ItemFriendBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_friend, parent,false)
        return  ViewHoler(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHoler){
            holder.bind(listFriend[position])
        }else if (holder is Header){
            holder.bind()
        }
    }

    override fun getItemCount(): Int {
        if (listFriend.size>0){
            return listFriend.size +1
        }
        return  1
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0){
            return  TYPE_HEADER
        }
        return  TYPE_ITEM
    }
    companion object{
        val TYPE_HEADER = 0
        val TYPE_ITEM =1
    }
}