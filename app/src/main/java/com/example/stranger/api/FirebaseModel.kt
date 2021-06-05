
package com.example.stranger

import android.net.Uri
import android.util.Log
import com.example.stranger.mode.*
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
    fun upLoadAnh(data: ByteArray, mission: missionImg){
        val storage: StorageReference = storageRef.reference.child("${mAuth.currentUser.uid}").child(
            getKey())
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
    fun  itemHomeChange(missionChange: missionChange){
        home.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                var itemHome: ItemHome =snapshot.getValue(ItemHome::class.java) as ItemHome
                missionChange.add(itemHome)

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                var itemHome: ItemHome =snapshot.getValue(ItemHome::class.java) as ItemHome
                missionChange.change(itemHome)

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                var itemHome: ItemHome =snapshot.getValue(ItemHome::class.java) as ItemHome
                missionChange.remove(itemHome)

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    fun upLoadListImg(listData: List<Uri>, misson: missionListImg){

        var  urlList : ArrayList<String> = arrayListOf()
        for (i in listData.indices) {
            if (i == listData.size-1) {
                var key: String=  getKey()
                val storage: StorageReference = storageRef.reference.child("${mAuth.currentUser.uid}").child(key)
                uploadTask = storage.putFile(listData[i])
                uploadTask.addOnSuccessListener {
                    storage.downloadUrl.addOnCompleteListener { task ->
                        val url: String = task.result.toString()
                        urlList.add(url)
                        misson.success(urlList)
                    }
                }
            }
                var key: String=  getKey()
                    val storage: StorageReference = storageRef.reference.child("${mAuth.currentUser.uid}").child(key)
                    uploadTask = storage.putFile(listData[i])
                    uploadTask.addOnSuccessListener {
                        storage.downloadUrl.addOnCompleteListener { task ->
                            val url: String = task.result.toString()
                            urlList.add(url)

                }
            }
            }
        }


    fun addItemHome( itemHome: ItemHome , mission: mission){
        home.child(itemHome.key).setValue(itemHome)
            .addOnSuccessListener{
                mission.succsess()
            }.addOnFailureListener {
                mission.failuer()
            }
    }

    fun getAllHome(missionHome: missionHome){
        var listItemHome: MutableList<ItemHome> = mutableListOf()
        home.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children){
                    val itemHome : ItemHome = item.getValue(ItemHome::class.java) as ItemHome
                    listItemHome.add(itemHome)
                }
                missionHome.succsess(listItemHome)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    fun getItemHome(key: String, missionItemHome: missionItemHome){
        home.child(key).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var itemHome :ItemHome = snapshot.getValue(ItemHome::class.java) as ItemHome
                Log.d("hehe", "$itemHome")
                missionItemHome.succsess(itemHome)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    fun getAllComment(key: String , missionComment: missionComment ){
        var listItemComment: ArrayList<Comment> = arrayListOf()
        home.child(key).child("listComment").addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children){
                    val comment: Comment = item.getValue(Comment::class.java) as Comment
                    listItemComment.add(comment)
                }
                missionComment.succsess(listItemComment)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    fun updateComment(key: String, comment: Comment , keyComment: String) {
        Log.d("kooug","${key}")
        home.child(key).child("listComment").child(keyComment).setValue(comment)
    }
    fun updateReplyComment(key: String,keyComment: String,keyReplyComment:String, replyComment: ReplyComment){
        home.child(key).child("listComment").child(keyComment).child("listReplyComment")
            .child(keyReplyComment).setValue(replyComment)
    }
    fun commentChange(
        key: String,
        missionCommentChange: missionCommentChange){
        home.child(key).child("listComment")
            .addChildEventListener(object : ChildEventListener{
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val comment: Comment = snapshot.getValue(Comment::class.java) as Comment
                 Log.d("khkh","$comment")
                    missionCommentChange.add(comment)
                }
                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    val comment: Comment = snapshot.getValue(Comment::class.java) as Comment
                    Log.d("comment", "$comment")
                    missionCommentChange.change(comment)
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    val comment: Comment = snapshot.getValue(Comment::class.java) as Comment
                    missionCommentChange.remove(comment)
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    missionCommentChange.cancel(error.message)
                }

            })
    }
    fun replyCommentChange(key: String, keyComment: String,missionReplyCommentChange: missionReplyCommentChange){
        home.child(key).child("listComment").child(keyComment).child("listReplyComment")
            .addChildEventListener(object : ChildEventListener{
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                  var replyComment =  snapshot.getValue(ReplyComment::class.java) as ReplyComment
                    missionReplyCommentChange.add(replyComment)
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    var replyComment =  snapshot.getValue(ReplyComment::class.java) as ReplyComment
                    missionReplyCommentChange.change(replyComment)
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    var replyComment =  snapshot.getValue(ReplyComment::class.java) as ReplyComment
                    missionReplyCommentChange.remove(replyComment)
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }
    fun  updateLikeItemhome(key: String , lisUserLike: ArrayList<String>){
        home.child(key).child("listUserLike").setValue(lisUserLike)
    }
    fun getAllReplyComment(key: String, keyComment: String , missionReplyCommment: missionReplyCommment){
        var lisReplyComment : ArrayList<ReplyComment> = arrayListOf()
        home.child(key).child("listComment").child(keyComment).child("listReplyComment")
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (item in snapshot.children){
                        var replyComment: ReplyComment = item.getValue(ReplyComment::class.java) as ReplyComment
                        lisReplyComment.add(replyComment)
                    }
                    missionReplyCommment.succses(lisReplyComment)
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
    }
    fun getItemComment(key : String, keyComment: String, missionGetItemComment: msissionGetItemComment){
        home.child(key).child("listComment").child(keyComment)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    var comment : Comment = snapshot.getValue(Comment::class.java) as Comment
                    missionGetItemComment.succsess(comment)
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }
    fun addRequetFriend(uid: Uid){
        proflie.child(getUser().uid).child("friendRequest").child(uid.key).setValue(uid)
    }

    fun addFriend(key: String,uid: Uid, mission: mission){
        proflie.child(key).child("listFriend").child(uid.key).setValue(uid)
            .addOnSuccessListener {
                mission.succsess()
            }.addOnCanceledListener {
                mission.failuer()
            }
    }
    fun addFriendRequest(key: String,uid: Uid, mission: mission){
        proflie.child(key).child("friendRequest").child(uid.key).setValue(uid)
            .addOnCanceledListener {
                mission.failuer()
            }.addOnSuccessListener {
                mission.succsess()
            }

    }
    fun deleteConfirmRequest(key: String, keyUid: String,mission: mission){
        proflie.child(key).child("friendRequest").child(keyUid).removeValue()
            .addOnSuccessListener {
                mission.succsess()
            }.addOnCanceledListener {
                mission.failuer()
            }
    }
    fun removeFriend(key: String , keyFriend: String,mission: mission){
        proflie.child(key).child("listFriend").child(keyFriend).removeValue()
            .addOnSuccessListener {
                mission.succsess()
            }.addOnCanceledListener {
                mission.failuer()
            }
    }
    fun getProFile(key :  String ,missionProFile: missionProFile ){
        proflie.child(key).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val proFile :ProFile = snapshot.getValue(ProFile::class.java) as ProFile
                missionProFile.succsess(proFile)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun getListProFile(missionAllProFile: missonAllProFile) {
           var  listProFile : ArrayList<ProFile> = arrayListOf()
            proflie.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (item in snapshot.children){
                        val proFile: ProFile = item.getValue(ProFile::class.java) as ProFile
                        listProFile.add(proFile)
                    }
                    missionAllProFile.succsess(listProFile)
                }
                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    fun  proFileChange(key :  String ,missionProFile: missionProFile ){
        proflie.child(key).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val proFile :ProFile = snapshot.getValue(ProFile::class.java) as ProFile
                missionProFile.succsess(proFile)
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }
    fun getRequestFirend(missionUid: missionUid){
        var listUid: MutableList<Uid> = mutableListOf()
        proflie.child(getUser().uid).child("friendRequest").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children){
                    val uid : Uid = item.getValue(Uid::class.java) as Uid
                    listUid.add(uid)
                    missionUid.succsess(listUid)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun addPostsProFile(key: String, posts: Posts, mission: mission){
        proflie.child(key).child("listPosts").child(posts.key).setValue(posts)
            .addOnSuccessListener {
                mission.succsess()
            }.addOnCanceledListener {
                mission.failuer()
            }
    }
    fun addItemMessage(key: String, itemMessage: ItemMessage){
        messenger.child(key).setValue(itemMessage)
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
    fun success(urlList: ArrayList<String>)
    fun failure(exception: Exception)
    fun load()
}interface missionHome{
    fun succsess(listItemHome:MutableList<ItemHome>)
    fun failure(err : String)
}
interface missionChange{
    fun add (itemHome: ItemHome)
    fun change(itemHome: ItemHome)
    fun remove(itemHome: ItemHome)
    fun cancel(err: String)
}
interface missionComment{
    fun succsess(listItemComment: ArrayList<Comment>)
    fun faile()
}
interface  missionCommentChange{
    fun add (comment: Comment)
    fun change(comment: Comment)
    fun remove(comment: Comment)
    fun cancel(err: String)
}
interface missionReplyCommment{
    fun succses(listReplyComment: ArrayList<ReplyComment>)
    fun failure(err : String)
}
interface  msissionGetItemComment{
    fun succsess(comment: Comment)
    fun failure(err : String)
}
interface  missionProFile{
    fun succsess(proFile: ProFile)
}
interface missionItemHome{
    fun succsess(itemHome: ItemHome)
}
interface  missionReplyCommentChange{
    fun add (replyComment: ReplyComment)
    fun change(replyComment: ReplyComment)
    fun remove(replyComment: ReplyComment)
    fun cancel(err: String)
}
interface missonAllProFile{
    fun succsess(listProFile: ArrayList<ProFile>)
    fun faile()
}interface missionUid{
    fun succsess (listUid : MutableList<Uid>)
    fun faile()
}
