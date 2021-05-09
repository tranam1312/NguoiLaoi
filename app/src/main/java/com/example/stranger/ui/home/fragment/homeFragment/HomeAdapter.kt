package com.example.stranger.ui.home.fragment.homeFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.stranger.databinding.ItemHomeBinding
import com.example.stranger.R
import com.example.stranger.mode.ItemHome
import me.relex.circleindicator.CircleIndicator

class HomeAdapter(private val onClickLike:(ItemHome)->Unit, private val onClickCommnet: (ItemHome) -> Unit,
private val onClickShare: (ItemHome) -> Unit, private var userId:String, private val onClickItemImg:(String)->Unit):RecyclerView.Adapter<HomeAdapter.ViewHolder> (){
    private lateinit var viewPage :ViewPager
    private lateinit var imgViewPage: ImgViewPage
    private lateinit var circleIndicator: CircleIndicator
    private var listItemHome : ArrayList<ItemHome> = arrayListOf()
    inner class ViewHolder(private  val itemHomeBinding: ItemHomeBinding):RecyclerView.ViewHolder(itemHomeBinding.root){
        var like : ImageButton = itemHomeBinding.like
        var comment : ImageButton =itemHomeBinding.comment
        var share: ImageButton = itemHomeBinding.share
        fun bind(itemHome: ItemHome){
            itemHomeBinding.appCompatTextView.setText(itemHome.userName)
            Glide.with(itemHomeBinding.root.context).load(itemHome.urlAvatar).into(itemHomeBinding.imageView)
            itemHomeBinding.appCompatTextView2.setText(itemHome.content)
            if (itemHome.urlList.size ==1){
                viewPage = itemHomeBinding.imageView2
                circleIndicator = itemHomeBinding.circleCenter
                imgViewPage = ImgViewPage(onClickItemImg)
                imgViewPage.setImgViewPage(itemHome.urlList)
                viewPage.adapter = imgViewPage
            } else if (itemHome.urlList.size >1){
                viewPage = itemHomeBinding.imageView2
                circleIndicator = itemHomeBinding.circleCenter
                imgViewPage = ImgViewPage(onClickItemImg)
                imgViewPage.setImgViewPage(itemHome.urlList)
                viewPage.adapter = imgViewPage
                circleIndicator.setViewPager(viewPage)
                imgViewPage.registerDataSetObserver(circleIndicator.dataSetObserver)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding: ItemHomeBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_home, parent, false)
        return  ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listItemHome[position])
        holder.like.setOnClickListener {
            onClickLike(listItemHome[position])
            notifyItemChanged(position)
        }
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
    fun setAdapter(listItemHome:List<ItemHome>){
        this.listItemHome = listItemHome as ArrayList<ItemHome>
        notifyDataSetChanged()
    }

}