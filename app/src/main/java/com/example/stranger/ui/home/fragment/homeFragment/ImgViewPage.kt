package com.example.stranger.ui.home.fragment.homeFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.stranger.R
import com.example.stranger.databinding.ImgViewBinding

class ImgViewPage(private val onClickItemImg:(String)->Unit):PagerAdapter() {
    private  var listUrl: ArrayList<String> = arrayListOf()
    override fun getCount(): Int {
        return listUrl.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return  view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val  binding : ImgViewBinding  = DataBindingUtil.inflate(LayoutInflater.from(container.context), R.layout.img_view,container,false)
        if (listUrl.get(position) != null){
            Glide.with(binding.root.context).load(listUrl.get(position)).into(binding.imageView2)
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

    fun setImgViewPage(listUrl: ArrayList<String>){
        this.listUrl = listUrl
        notifyDataSetChanged()
    }
}