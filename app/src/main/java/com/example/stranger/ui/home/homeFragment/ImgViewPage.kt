package com.example.stranger.ui.home.fragment.homeFragment

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.stranger.R
import com.example.stranger.databinding.ImgViewBinding

class ImgViewPage(private val onClickItemImg:(String)->Unit):PagerAdapter() {
    private  var listUrl: ArrayList<String> = arrayListOf()


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return  view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val  binding : ImgViewBinding  = DataBindingUtil.inflate(LayoutInflater.from(container.context), R.layout.img_view,container,false)
        if (listUrl[position] != null){
            Glide.with(binding.root).load(listUrl[position]).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(binding.imageView2)
        }
        binding.root.setOnClickListener {
            onClickItemImg(listUrl[position])
        }
        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return listUrl.size
    }

    fun setImgViewPage(listUrl: ArrayList<String>){
        this.listUrl = listUrl
        notifyDataSetChanged()
    }
}