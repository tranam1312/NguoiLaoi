package com.example.stranger

import com.example.stranger.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask


class FirebaseModel {
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val storageRef = FirebaseStorage.getInstance()
    val storage: StorageReference = storageRef.reference.child("${mAuth.currentUser.uid}")
    private lateinit var uploadTask : UploadTask
    var database = FirebaseDatabase.getInstance()
    var total = database.getReference("Stranger")
    var user = total.child("User")
    val home = total.child("Home")
    val messenger = total.child("Mesenger")

    fun getUser(): FirebaseUser {
        return mAuth.currentUser
    }
    fun newFrofile(user: User){

    }
    fun upLoadAnh(data: ByteArray, mission: mission){

        uploadTask = storage.putBytes(data)
        uploadTask.addOnFailureListener {
            mission.failure(it)
        }.addOnSuccessListener {
            mission.success(it)
        }.addOnCompleteListener {
            mission.urlDowload(it)
        }.addOnProgressListener {
            mission.load(it)
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
    fun success(task: UploadTask.TaskSnapshot)
    fun failure(exception: Exception)
    fun urlDowload(task: Task<UploadTask.TaskSnapshot>)
    fun load(uploadTask: UploadTask.TaskSnapshot)

}