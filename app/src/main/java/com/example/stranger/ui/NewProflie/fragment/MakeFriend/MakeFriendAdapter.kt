package com.example.stranger.ui.NewProflie.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.stranger.R
import com.example.stranger.databinding.ItemAddRequestFriendBinding
import com.example.stranger.databinding.ItemConfirmFriendBinding
import com.example.stranger.databinding.ItemHeaderFriendBinding
import com.example.stranger.missionProFile
import com.example.stranger.mode.ProFile
import com.example.stranger.mode.Uid
import com.example.stranger.repository.Repository

class MakeFriendAdapter(private val addOnClick:(ProFile)->Unit ,
                        private  var onCorpseClick:(Uid)->Unit ,
                        private val onCLickDeleteAddRequest: (ProFile)->Unit,
                        private val onClickDeleteConfirmRequest:(Uid)->Unit,
                        private val onClickItemAddRequest:(ProFile)->Unit,
                        private val onClickItemConfirmRequest:(Uid)->Unit
)
    :RecyclerView.Adapter<RecyclerView.ViewHolder> (){
    private var listRequestFriend: ArrayList<Uid> = arrayListOf()
    private var listAddRequset: ArrayList<ProFile> = arrayListOf()
    private val repository = Repository()
    inner  class ViewHolderAddFriend(private val addRequestFriendBinding: ItemAddRequestFriendBinding) : RecyclerView.ViewHolder(addRequestFriendBinding.root){
        fun bind(proFile: ProFile){
            repository.getProFile(proFile.uid, object : missionProFile{
                override fun succsess(proFile: ProFile) {
                    Glide.with(addRequestFriendBinding.root).load(proFile.imgUrlAvatar).diskCacheStrategy(
                        DiskCacheStrategy.AUTOMATIC).into(addRequestFriendBinding.avatarRequestFriend)
                    addRequestFriendBinding.nameAddRequestFriend.text = proFile.name
                }

            })
            addRequestFriendBinding.add.setOnClickListener {
                addOnClick(proFile)
            }
            addRequestFriendBinding.delete.setOnClickListener {
                onCLickDeleteAddRequest(proFile)
            }
            addRequestFriendBinding.item.setOnClickListener {
                onClickItemAddRequest(proFile)
            }


        }
    }
    inner class ViewHolderConfirm(private val itemConfirmFriendBinding: ItemConfirmFriendBinding) : RecyclerView.ViewHolder(itemConfirmFriendBinding.root){
        fun bind(uid:Uid){
            repository.getProFile(uid.uid, object : missionProFile{
                override fun succsess(proFile: ProFile) {
                    Glide.with(itemConfirmFriendBinding.root).load(uid).diskCacheStrategy(
                        DiskCacheStrategy.AUTOMATIC).into(itemConfirmFriendBinding.confirmFriend.avatarRequestFriend)
                    itemConfirmFriendBinding.confirmFriend.nameAddRequestFriend.text = proFile.name
                }
            })
            itemConfirmFriendBinding.confirmFriend.add.text = "Confirm"
            itemConfirmFriendBinding.confirmFriend.add.setOnClickListener {
                onCorpseClick(uid)
            }
            itemConfirmFriendBinding.confirmFriend.delete.setOnClickListener {
                onClickDeleteConfirmRequest(uid)
            }
            itemConfirmFriendBinding.confirmFriend.item.setOnClickListener {
                onClickItemConfirmRequest(uid)
            }
        }
    }
    inner class ViewHolderConfirmHeader(private val itemHeaderFriendBinding: ItemHeaderFriendBinding): RecyclerView.ViewHolder(itemHeaderFriendBinding.root){
        fun bind(size: Int){
            itemHeaderFriendBinding.headerTitle.text = "Friend request +  $size"
        }
    }
    inner class ViewHolderAddFriendHeader(private val itemHeaderFriendBinding: ItemHeaderFriendBinding): RecyclerView.ViewHolder(itemHeaderFriendBinding.root){
        fun bind(){
            itemHeaderFriendBinding.headerTitle.text = "People you may know" }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == HEAD_ITEM_REQUETS){
            val binding: ItemHeaderFriendBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_header_friend, parent, false)
            return ViewHolderConfirmHeader(binding)
        }else if (viewType == ITEM_REQUEST){
            val binding : ItemConfirmFriendBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_confirm_friend , parent, false)
            return ViewHolderConfirm(binding)
        }else if (viewType == HEADER_ITEM_ADD){
            val binding: ItemHeaderFriendBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_header_friend, parent, false)
            return  ViewHolderAddFriendHeader(binding)
        }
      val binding : ItemAddRequestFriendBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
          R.layout.item_add_request_friend,parent, false)
        return ViewHolderAddFriend(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolderConfirmHeader){
            holder.bind(listRequestFriend.size)
        }else if (holder is ViewHolderConfirm){
            holder.bind(listRequestFriend[position])
        }else if (holder is ViewHolderAddFriendHeader){
            holder.bind()
        }else if (holder is ViewHolderAddFriend){
            holder.bind(listAddRequset[position])
        }
    }

    override fun getItemCount(): Int {
        if (listAddRequset.size ==0 && listRequestFriend.size== 0 ){
            return 0
        }
        return listAddRequset.size + listRequestFriend.size +2
    }

    override fun getItemViewType(position: Int): Int {
        if(position ==0 ){
            return HEAD_ITEM_REQUETS
        }else if (position <= listRequestFriend.size){
            return ITEM_REQUEST
        }else if (position == listRequestFriend.size+1){
            return HEADER_ITEM_ADD
        }
        return ITEM_ADD
    }
    fun setRequest(listRequsestFriend: ArrayList<Uid>){
        this.listRequestFriend = listRequestFriend
        notifyDataSetChanged()
    }
    fun setAdd(listAddRequset:ArrayList<ProFile>){
        this.listAddRequset = listAddRequset
        notifyDataSetChanged()
    }
    companion object{
        val HEAD_ITEM_REQUETS = 0
        val ITEM_REQUEST =1
        val HEADER_ITEM_ADD =2
        val ITEM_ADD =3
    }
}