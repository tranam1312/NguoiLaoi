package com.example.stranger.ui.messenger

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stranger.databinding.MessengerFragmentBinding
import com.example.stranger.mode.ItemMessenger

class MessengerAdapter: RecyclerView.Adapter<MessengerAdapter.ViewHolder>() {
    inner class ViewHolder(messengerFragmentBinding: MessengerFragmentBinding) : RecyclerView.ViewHolder(messengerFragmentBinding.root){
        fun bind(itemMessenger: ItemMessenger){

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