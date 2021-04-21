package com.example.stranger.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stranger.databinding.ItemHomeBinding

import com.example.stranger.model.Item

class HomeAdapter:RecyclerView.Adapter<HomeAdapter.ViewHolder> (){
    inner class ViewHolder(itemHomeBinding: ItemHomeBinding):RecyclerView.ViewHolder(itemHomeBinding.root){
        fun bin(item: Item){

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}