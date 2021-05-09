package com.example.stranger.ui.home.fragment.newPostFragment

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.stranger.R
import com.example.stranger.databinding.ImgSelectNewpostBinding

class ImgURIAdapter(private val delete:(Uri)->Unit):RecyclerView.Adapter<ImgURIAdapter.ViewHolder>(){
    private var  lits: ArrayList<Uri> = arrayListOf()
    inner class ViewHolder( private val imgSelectNewpostBinding: ImgSelectNewpostBinding):RecyclerView.ViewHolder(imgSelectNewpostBinding.root){
        fun bind(uri: Uri){
            Glide.with(imgSelectNewpostBinding.root.context).load(uri).into(imgSelectNewpostBinding.imageView4)
            imgSelectNewpostBinding.delete.setOnClickListener {
                delete(uri)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding:ImgSelectNewpostBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.img_select_newpost, parent, false)
        return  ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lits[position])
    }

    override fun getItemCount(): Int {
     return lits.size
    }
    fun setAdater(list: ArrayList<Uri>){
        this.lits = list
        notifyDataSetChanged()
    }
}