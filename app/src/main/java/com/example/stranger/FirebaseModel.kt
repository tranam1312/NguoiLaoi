package com.example.stranger

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.stranger.mode.ItemHome
import com.example.stranger.mode.ProFile
import com.example.stranger.mode.RequsestFriend
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
    var liveDataListItemHome : MutableLiveData<ArrayList<ItemHome>>  = MutableLiveData()
    var listItemHome : ArrayList<ItemHome> = arrayListOf()

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
    fun getAllHome(missionHome: missionHome){
        home.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children){
                    val itemHome:ItemHome = item.getValue(ItemHome::class.java) as ItemHome
                    listItemHome.add(itemHome)
                    missionHome.succsess(listItemHome)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
    fun itemHomeChange(key : String, missionChange: missionChange){
        home.child(key).addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
               val itemHome : ItemHome = snapshot.getValue(ItemHome::class.java) as ItemHome
                missionChange.add(itemHome)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val itemHome : ItemHome = snapshot.getValue(ItemHome::class.java) as  ItemHome
                missionChange.change(itemHome)
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val  itemHome : ItemHome = snapshot.getValue(ItemHome::class.java) as ItemHome
                missionChange.remove(itemHome)
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                val  itemHome : ItemHome = snapshot.getValue(ItemHome::class.java) as ItemHome
                missionChange.move(itemHome)
            }

            override fun onCancelled(error: DatabaseError) {
               missionChange.cancel(error.message)
            }

        })
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



    fun getListProFile(listUid: ArrayList<String>) {
        var listLiveData: MutableLiveData<ArrayList<ProFile>> = MutableLiveData()
        var listProFile: ArrayList<ProFile> = arrayListOf()
        for (item in listUid) {
            val query = proflie.child(getUser().uid)
            query.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.getValue(ProFile::class.java)

                }
                override fun onCancelled(error: DatabaseError) {

                }

            })
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
}interface missionHome{
    fun succsess(listItemHome: ArrayList<ItemHome>)
    fun faile()
}
interface missionChange{
    fun add (itemHome: ItemHome)
    fun change(itemHome: ItemHome)
    fun remove(itemHome: ItemHome)
    fun move(itemHome: ItemHome)
    fun cancel(err: String)
}