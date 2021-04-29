package com.example.stranger

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.stranger.model.ItemHome
import com.example.stranger.model.ProFile
import com.example.stranger.model.RequsestFriend
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask


class FirebaseModel {
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val storageRef = FirebaseStorage.getInstance()
    private lateinit var uploadTask : UploadTask
    var database = FirebaseDatabase.getInstance()
    private var total = database.getReference("Stranger")
    private var proflie = total.child("User")
    private val home = total.child("Home")
    private val messenger = total.child("Mesenger")
    private val requestFirend= total.child("RequestFriend")
    private var  urlList : ArrayList<String> = arrayListOf()

    fun getUser(): FirebaseUser {
        return mAuth.currentUser
    }
    fun newFrofile(user: ProFile, mission: mission){
        proflie.child(mAuth.currentUser.uid).setValue(user). addOnSuccessListener {
            mission.succsess()
        }.addOnFailureListener {
            mission.failuer()
        }
    }

    fun getKey():String{
        val key : String = total.push().key.toString()
        return key
    }
    fun upLoadAnh(data: ByteArray, key: String, mission: missionImg){
        val storage: StorageReference = storageRef.reference.child("${mAuth.currentUser.uid}").child(
            getKey()
        )
        uploadTask = storage.putBytes(data)
        uploadTask.addOnFailureListener {
            mission.failure(it)
        }.addOnSuccessListener {
            storage.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
                val url: String = task.result.toString()
                mission.success(url)
                Log.d("kkk", "${url}")
            })

        }
        }
    fun upLoadListImg(listData: ArrayList<Uri>, misson: missionListImg){
        var listKey:ArrayList<String> = arrayListOf()
        for (i in listData) {
            var key: String=  getKey()
            val storage: StorageReference = storageRef.reference.child("${mAuth.currentUser.uid}").child(key)
            uploadTask = storage.putFile(i)
            uploadTask.addOnSuccessListener {
                storage.downloadUrl.addOnCompleteListener { task ->
                    val url: String = task.result.toString()
                    urlList.add(url)
                }
                listKey.add(key)

                if (listData.indexOf(i) == (listData.size - 1)) {
                    misson.success(urlList, listKey)
                }
            }
        }
    }
    fun getAllHome():ArrayList<ItemHome>{
        var listItemHome : ArrayList<ItemHome> = arrayListOf()
        home.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children){
                   val itemHome : ItemHome =item.getValue(ItemHome::class.java) as ItemHome
                    listItemHome.add(itemHome)
                    Log.d("itemHome", "${itemHome}")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        return listItemHome
    }
    fun addRequetFriend(requsestFriend: RequsestFriend){
        requestFirend.child(mAuth.currentUser.uid).setValue(requsestFriend)
    }
    fun updateItemHome(key : String, itemHome: ItemHome){
        home.child(key).setValue(itemHome)
    }
    fun addItemHome(key: String, itemHome: ItemHome , mission: mission){
        home.child(key).setValue(itemHome)
                .addOnSuccessListener{
                    mission.succsess()
                }.addOnFailureListener {
                    mission.failuer()
                }
    }








    companion object{
        const val PROFILE = "Profile"
        const val HOME = "Home"
        const val FRIEND = "Friend"
        const val MESSENGER = "Messenger"
        const val APP= "Stranger"
    }
}
interface mission{
    fun succsess()
    fun failuer()
}
interface missionImg{
    fun success(url: String)
    fun failure(exception: Exception)
}
interface missionListImg{
    fun success(urlList: ArrayList<String>,listKey:ArrayList<String>)
    fun failure(exception: Exception)
    fun load()
}