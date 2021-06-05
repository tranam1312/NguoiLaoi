package com.example.stranger.ui.home.fragment.homeFragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stranger.*
import com.example.stranger.mode.ItemHome
import com.example.stranger.repository.Repository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private var  repository: Repository  = Repository()
    private var  listItem: MutableList<ItemHome> =  mutableListOf()
    fun getUid()= repository.getUserLogin()
    fun getKey()= repository.getkey()
    fun getAllItemHome(missionHome: missionHome)= viewModelScope.launch {
        repository.getAllHome(object : missionHome {
            override fun succsess(listItemHome: MutableList<ItemHome>) {
                if (listItemHome.size !=0){
                listItem = listItemHome.reversed() as MutableList<ItemHome>
                missionHome.succsess(listItem)
                }
            }

            override fun failure(err: String) {
                TODO("Not yet implemented")
            }

        })

    }
    fun itemHomeChange(itemHomeChange: itemHomeChange)= viewModelScope.launch{
        repository.itemHomeChang(object : missionChange{
            override fun add(itemHome: ItemHome) {
                if (listItem.indexOf(itemHome)==-1){
                    listItem.add(0, itemHome)
                    itemHomeChange.add(0)
                    Log.d("hehe", "$listItem")
                }
            }

            override fun change(itemHome: ItemHome){
                for (i in 0 until listItem.size){
                    var item  = listItem[i]
                    if (item.key == itemHome.key){
                        item.listUserLike = itemHome.listUserLike
                        item.listCommnent = itemHome.listCommnent
                        item.keyShare = itemHome.keyShare
                        if(item.urlList!!.size > itemHome.urlList!!.size||item.urlList!!.size < itemHome.urlList!!.size){
                            item.urlList = itemHome.urlList
                         }
                        itemHomeChange.chang(i)
                        break
                    }
                }
            }

            override fun remove(itemHome: ItemHome) {
                for (i in 0 until listItem.size){
                    if (listItem[i].key == itemHome.key){
                        listItem.removeAt(i)
                        itemHomeChange.remove(i)
                        break
                    }
                }
            }

            override fun cancel(err: String) {
                itemHomeChange.cancle(err)
            }

        })
    }
    fun updateLikeItemHome(itemHome: ItemHome)= viewModelScope.launch{
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
    fun addItemHome(key: String, itemHome: ItemHome, mission: mission)= viewModelScope.launch {
        repository.addItemHome(itemHome, mission)
    }
    fun proFile(key: String, missionProFile: missionProFile)= viewModelScope.launch{
        repository.getProFile(key,missionProFile)
    }
}
interface itemHomeChange {
    fun add(index : Int)
    fun chang(index: Int)
    fun remove(index: Int)
    fun cancle(err: String)
}
