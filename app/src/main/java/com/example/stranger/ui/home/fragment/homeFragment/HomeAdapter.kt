package com.example.stranger.ui.home.fragment.homeFragment

import android.annotation.SuppressLint
import android.content.ClipData
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.stranger.databinding.ItemHomeBinding
import com.example.stranger.R
import com.example.stranger.mode.ItemHome
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class HomeAdapter(private val onClickLike:(ItemHome)->Unit, private val onClickCommnet: (ItemHome) -> Unit,
private val onClickShare: (ItemHome) -> Unit, private var userId:String, private val onClickItemImg:(String)->Unit):RecyclerView.Adapter<HomeAdapter.ViewHolder> (){
    private var listItemHome : MutableList<ItemHome> = mutableListOf()
    private lateinit var binding: ItemHomeBinding
    private  lateinit var viewPage :ViewPager
    private  lateinit var imgViewPage: ImgViewPage
    private lateinit var circleIndicator: WormDotsIndicator
    inner class ViewHolder(private  val itemHomeBinding: ItemHomeBinding):RecyclerView.ViewHolder(itemHomeBinding.root){
        var comment : Button =itemHomeBinding.comment
        var share: Button = itemHomeBinding.share
        @SuppressLint("ResourceAsColor")
        fun bind(itemHome: ItemHome){
            itemHomeBinding.appCompatTextView.setText(itemHome.userName)
            Glide.with(itemHomeBinding.root.context).load(itemHome.urlAvatar).diskCacheStrategy(
                DiskCacheStrategy.ALL).into(itemHomeBinding.imageView)
            itemHomeBinding.appCompatTextView2.setText(itemHome.content)
            if (itemHome.urlList.size ==1){
                circleIndicator.setViewPager(viewPage)
                viewPage.adapter = imgViewPage
                imgViewPage.setImgViewPage(itemHome.urlList)
                itemHomeBinding.wormDotsIndicator.visibility = View.GONE
            } else if (itemHome.urlList.size >1){
                imgViewPage.setImgViewPage(itemHome.urlList)

            }
            if (itemHome.listUserLike?.size !=0){
                val i : Int = itemHome.listUserLike!!.indexOf(userId)
                if (i > -1){
                    itemHomeBinding.like.setIconTintResource(R.color.deeppink)
                    itemHomeBinding.like.setTextColor(Color.parseColor("#FF1493"))
                } else if (i== -1){
                    itemHomeBinding.like.setIconTintResource(R.color.black)
                    itemHomeBinding.like.setTextColor(Color.parseColor("#FF000000"))
                }

            }else if (itemHome.listUserLike?.size ==0){
                itemHomeBinding.like.setIconTintResource(R.color.black)
                itemHomeBinding.like.setTextColor(Color.parseColor("#FF000000"))
            }
            itemHomeBinding.like.setOnClickListener {
                if (itemHome.listUserLike?.size !=0){
                    val i: Int = itemHome.listUserLike!!.indexOf(userId)
                    if (i > -1) {
                        itemHomeBinding.like.setIconTintResource(R.color.black)
                        itemHomeBinding.like.setTextColor(Color.parseColor("#FF000000"))
                    } else if (i ==-1){
                        itemHomeBinding.like.setIconTintResource(R.color.deeppink)
                        itemHomeBinding.like.setTextColor(Color.parseColor("#FF1493"))
                    }
                }else if (itemHome.listUserLike?.size ==0){
                    itemHomeBinding.like.setIconTintResource(R.color.deeppink)
                    itemHomeBinding.like.setTextColor(Color.parseColor("#FF1493"))
                }
                onClickLike(itemHome)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
     binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_home, parent, false)
        viewPage = binding.imageView2
        circleIndicator = binding.wormDotsIndicator
        imgViewPage = ImgViewPage (onClickItemImg)
        viewPage.adapter = imgViewPage
        circleIndicator.setViewPager(viewPage)
        return  ViewHolder(binding)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listItemHome[position])
        holder.comment.setOnClickListener {
            onClickCommnet(listItemHome[position])
        }
        holder.share.setOnClickListener {
            onClickShare(listItemHome[position])
        }
    }

    override fun getItemCount(): Int {
        return listItemHome.size

}
    fun setAdapter(listItemHome: MutableList<ItemHome>) {
        this.listItemHome = listItemHome
        notifyDataSetChanged()
    }
}