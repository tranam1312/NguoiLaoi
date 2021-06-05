package com.example.stranger.ui.home.fragment.homeFragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.stranger.databinding.ItemHomeBinding
import com.example.stranger.R
import com.example.stranger.databinding.ItemHomeShareBinding
import com.example.stranger.missionItemHome
import com.example.stranger.missionProFile
import com.example.stranger.mode.ItemHome
import com.example.stranger.mode.ProFile
import com.example.stranger.repository.Repository
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator



class HomeAdapter(private val onClickLike:(ItemHome)->Unit,
                  private val onClickCommnet: (ItemHome)->Unit ,
                  private val onClickShare: (ItemHome) -> Unit,
                  private val onClickViewProFile:(ItemHome)-> Unit,
                  private var userId:String, private val onClickItemImg:(String)->Unit,
                  private val onClickThem:(ItemHome)->Unit
):RecyclerView.Adapter<RecyclerView.ViewHolder> (){
    private var listItemHome : MutableList<ItemHome> = mutableListOf()
    private  lateinit var viewPage :ViewPager
    private  lateinit var imgViewPage: ImgViewPage
    private lateinit var circleIndicator: WormDotsIndicator
    private val repository : Repository = Repository()
    inner class ViewHolder(private  val itemHomeBinding: ItemHomeBinding):RecyclerView.ViewHolder(itemHomeBinding.root){
        @SuppressLint("ResourceAsColor")

        fun bind(itemHome: ItemHome){
            repository.getProFile(itemHome.userid, object : missionProFile{
                override fun succsess(proFile: ProFile) {
                    itemHomeBinding.name.text = proFile.name
                    Glide.with(itemHomeBinding.root.context).load(proFile.imgUrlAvatar).diskCacheStrategy(
                        DiskCacheStrategy.AUTOMATIC).into(itemHomeBinding.imageView)
                }
            })

            itemHomeBinding.container.setText(itemHome.content)
            if (itemHome.urlList?.size!! >0) {
                if (itemHome.urlList?.size!! ==1){
                    viewPage = itemHomeBinding.imageView2
                    circleIndicator = itemHomeBinding.wormDotsIndicator
                    imgViewPage =  ImgViewPage (onClickItemImg)
                    viewPage.adapter = imgViewPage
                    imgViewPage.setImgViewPage(itemHome.urlList!!)
                    itemHomeBinding.wormDotsIndicator.visibility = View.GONE
                }
                if (itemHome.urlList?.size!! >1){
                    viewPage = itemHomeBinding.imageView2
                    circleIndicator = itemHomeBinding.wormDotsIndicator
                    imgViewPage =  ImgViewPage (onClickItemImg)
                    viewPage.adapter = imgViewPage
                    imgViewPage.setImgViewPage(itemHome.urlList!!)
                    circleIndicator.setViewPager(viewPage)

                }
            }else{
                viewPage = itemHomeBinding.imageView2
                viewPage.visibility = View.GONE
                circleIndicator = itemHomeBinding.wormDotsIndicator
                circleIndicator.visibility = View.GONE
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
            if (itemHomeBinding.container !=null) {
                itemHomeBinding.container.visibility= View.VISIBLE
                itemHomeBinding.container.text = itemHome.content
            }
            else{
                itemHomeBinding.container.visibility= View.GONE
            }
            itemHomeBinding.like.setOnClickListener {
                if (itemHome.listUserLike?.size !=0){
                    val i: Int = itemHome.listUserLike!!.indexOf(userId)
                    if (i > -1) {
                        itemHomeBinding.like.setIconTintResource(R.color.black)
                        itemHomeBinding.like.setTextColor(Color.parseColor("#FF000000"))
                    }
                    if (i ==-1){
                        itemHomeBinding.like.setIconTintResource(R.color.deeppink)
                        itemHomeBinding.like.setTextColor(Color.parseColor("#FF1493"))
                    }
                }
                if (itemHome.listUserLike?.size ==0){
                    itemHomeBinding.like.setIconTintResource(R.color.deeppink)
                    itemHomeBinding.like.setTextColor(Color.parseColor("#FF1493"))
                }
                onClickLike(itemHome)
            }
            itemHomeBinding.comment.setOnClickListener {
                onClickCommnet(itemHome)
            }
            itemHomeBinding.share.setOnClickListener {
                onClickShare(itemHome)
            }
            itemHomeBinding.them.setOnClickListener {
                onClickThem(itemHome)
            }
            itemHomeBinding.imageView.setOnClickListener {
                onClickViewProFile(itemHome)
            }
            itemHomeBinding.name.setOnClickListener {
                onClickViewProFile(itemHome)
            }
        }
    }
    inner class ViewHolderShare(private val itemHomeShareBinding: ItemHomeShareBinding):RecyclerView.ViewHolder(itemHomeShareBinding.root){
        fun bindShare(itemHomeShare: ItemHome){
            repository.getProFile(itemHomeShare.userid, object : missionProFile{
                override fun succsess(proFile: ProFile) {
                    itemHomeShareBinding.name.text = proFile.name
                    Glide.with(itemHomeShareBinding.root.context).load(proFile.imgUrlAvatar).diskCacheStrategy(
                        DiskCacheStrategy.AUTOMATIC).into(itemHomeShareBinding.imgAvatar)

            repository.getItemHome(itemHomeShare.keyShare!!,object : missionItemHome{
                override fun succsess(itemHome: ItemHome) {
                    repository.getProFile(itemHome.userid, object :missionProFile{
                        override fun succsess(proFile: ProFile) {
                            itemHomeShareBinding.nameShare.text = proFile.name
                            Glide.with(itemHomeShareBinding.root).load(proFile.imgUrlAvatar).diskCacheStrategy(
                                DiskCacheStrategy.AUTOMATIC).into(itemHomeShareBinding.imageShare)
                        }
                    })


                    if (itemHome.content != null) {
                        itemHomeShareBinding.textViewShare.visibility = View.VISIBLE
                        itemHomeShareBinding.textViewShare.text = itemHome.content
                    }else if (itemHome.content == null){
                        itemHomeShareBinding.textViewShare.visibility = View.GONE
                    }
                    if (itemHome.urlList?.size!! >0) {
                        if (itemHome.urlList?.size!! ==1){
                            viewPage = itemHomeShareBinding.imageView2
                            circleIndicator = itemHomeShareBinding.wormDotsIndicator
                            imgViewPage =  ImgViewPage (onClickItemImg)
                            viewPage.adapter = imgViewPage
                            imgViewPage.setImgViewPage(itemHome.urlList!!)
                            itemHomeShareBinding.wormDotsIndicator.visibility = View.GONE
                        }
                        if (itemHome.urlList?.size!! >1){
                            Log.d("hehehe", "${itemHome.urlList}")
                            viewPage = itemHomeShareBinding.imageView2
                            circleIndicator = itemHomeShareBinding.wormDotsIndicator
                            imgViewPage =  ImgViewPage (onClickItemImg)
                            viewPage.adapter = imgViewPage
                            imgViewPage.setImgViewPage(itemHome.urlList!!)
                            circleIndicator.setViewPager(viewPage)
                            circleIndicator.visibility= View.VISIBLE
                        }
                    }
                    if (itemHome.urlList?.size!! ==0){
                        viewPage = itemHomeShareBinding.imageView2
                        viewPage.visibility = View.GONE
                        circleIndicator = itemHomeShareBinding.wormDotsIndicator
                        circleIndicator.visibility = View.GONE
                    }
                }

            })}})
              if (itemHomeShare.content !=null) {
                itemHomeShareBinding.container.visibility= View.VISIBLE
                itemHomeShareBinding.container.text = itemHomeShare.content
            }
            else{
                itemHomeShareBinding.container.visibility= View.GONE
            }

            if (itemHomeShare.listUserLike?.size !=0){
                val i : Int = itemHomeShare.listUserLike!!.indexOf(userId)
                if (i > -1){
                    itemHomeShareBinding.like.setIconTintResource(R.color.deeppink)
                    itemHomeShareBinding.like.setTextColor(Color.parseColor("#FF1493"))
                } else if (i== -1){
                    itemHomeShareBinding.like.setIconTintResource(R.color.black)
                    itemHomeShareBinding.like.setTextColor(Color.parseColor("#FF000000"))
                }

            }else if (itemHomeShare.listUserLike?.size ==0){
                itemHomeShareBinding.like.setIconTintResource(R.color.black)
                itemHomeShareBinding.like.setTextColor(Color.parseColor("#FF000000"))
            }
            itemHomeShareBinding.like.setOnClickListener {
                if (itemHomeShare.listUserLike?.size !=0){
                    val i: Int = itemHomeShare.listUserLike!!.indexOf(userId)
                    if (i > -1) {
                        itemHomeShareBinding.like.setIconTintResource(R.color.black)
                        itemHomeShareBinding.like.setTextColor(Color.parseColor("#FF000000"))
                    } else if (i ==-1){
                        itemHomeShareBinding.like.setIconTintResource(R.color.deeppink)
                        itemHomeShareBinding.like.setTextColor(Color.parseColor("#FF1493"))
                    }
                }else if (itemHomeShare.listUserLike?.size ==0){
                    itemHomeShareBinding.like.setIconTintResource(R.color.deeppink)
                    itemHomeShareBinding.like.setTextColor(Color.parseColor("#FF1493"))
                }
                onClickLike(itemHomeShare)
            }
            itemHomeShareBinding.comment.setOnClickListener {
                onClickCommnet(itemHomeShare)
            }
            itemHomeShareBinding.share.setOnClickListener {
                onClickShare(itemHomeShare)
            }
            itemHomeShareBinding.them.setOnClickListener {
                onClickThem(itemHomeShare)
            }
            itemHomeShareBinding.imgAvatar.setOnClickListener {
                onClickViewProFile(itemHomeShare)
            }
            itemHomeShareBinding.name.setOnClickListener {
                onClickViewProFile(itemHomeShare)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_SHARE){
             val   itemHomeShareBinding: ItemHomeShareBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_home_share, parent, false)

            return ViewHolderShare(itemHomeShareBinding)
        }
        val itemHomeBinding : ItemHomeBinding  = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_home, parent, false)

        return  ViewHolder(itemHomeBinding)

    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
         if (holder is ViewHolderShare){
            holder.bindShare(listItemHome[position])
        }
        if (holder is ViewHolder){
            holder.bind(listItemHome[position])
        }
    }

    override fun getItemCount(): Int {
        if (listItemHome.size != null) {
            return listItemHome.size
        }
        return 0
}
    fun setAdapter(listItemHome: MutableList<ItemHome>) {
        this.listItemHome = listItemHome
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        if (listItemHome[position].keyShare !=""){
            return TYPE_SHARE
        }
            return TYPE_ITEM
    }
    companion object{
        private const  val TYPE_SHARE = 0
        private const val  TYPE_ITEM = 1
    }
}