package com.example.stranger.ui.home.fragment.homeFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stranger.FirebaseModel
import com.example.stranger.missionChange
import com.example.stranger.missionHome
import com.example.stranger.mode.ItemHome
import com.example.stranger.repository.Repository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private var  repository: Repository  = Repository()
    private var listItem: MutableList<ItemHome> =  mutableListOf()
    fun getUid()= repository.getUserLogin()
    fun getAllItemHome(missionHome: missionHome) {
        repository.getAllHome(object : missionHome {
            override fun succsess(listItemHome: MutableList<ItemHome>) {
                listItem = listItemHome.reversed() as MutableList<ItemHome>
                missionHome.succsess(listItem)
            }
            override fun faile() {
            }

        })

    }
    fun itemHomeChange(itemHomeChange: itemHomeChange){
        repository.itemHomeChang(object : missionChange{
            override fun add(itemHome: ItemHome) {
                if (listItem.indexOf(itemHome)==-1){
                    listItem.add(0, itemHome)
                    itemHomeChange.add(0)
                }
            }

            override fun change(itemHome: ItemHome) {
                for (i in 0 until listItem.size){
                    var item  = listItem[i]
                    if (item.key == itemHome.key){
                        item.listUserLike = itemHome.listUserLike
                        item.listCommnent = item.listCommnent
                        if(item.urlList.size > itemHome.urlList.size||item.urlList.size < itemHome.urlList.size){
                            item.urlList = itemHome.urlList
                        }
                        itemHomeChange.chang(i)
                    }
                }
            }

            override fun remove(itemHome: ItemHome) {
                for (i in 0 until listItem.size){
                    if (listItem[i].key == itemHome.key){
                        listItem.removeAt(i)
                        itemHomeChange.remove(i)
                    }
                }
            }

            override fun cancel(err: String) {
                itemHomeChange.cancle(err)
            }

        })
    }

    fun updateLikeItemHome(itemHome: ItemHome){
        if (itemHome.listUserLike?.size != null) {
            val i: Int = itemHome.listUserLike!!.indexOf(getUid().uid)
            if (i > -1) {
                itemHome.listUserLike!!.remove(getUid().uid)
                repository.updateLikeItemHome(itemHome.key, itemHome.listUserLike!!)
            } else if (i ==-1){
                itemHome.listUserLike!!.add(getUid().uid)
                repository.updateLikeItemHome(itemHome.key,itemHome.listUserLike!!)
            }
        }else{
            itemHome.listUserLike!!.add(getUid().uid)
            repository.updateLikeItemHome(itemHome.key,itemHome.listUserLike!!)
        }
    }
}
interface itemHomeChange {
    fun add(index : Int)
    fun chang(index: Int)
    fun remove(index: Int)
    fun cancle(err: String)
}
