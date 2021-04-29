//package com.example.stranger.service
//
//import android.app.Service
//import android.content.Intent
//import android.graphics.Bitmap
//import android.net.Uri
//import android.os.IBinder
//import android.provider.MediaStore
//import androidx.core.app.NotificationCompat
//import androidx.core.app.NotificationManagerCompat
//import com.example.stranger.local.Preferences
//import com.example.stranger.mission
//import com.example.stranger.missionListImg
//import com.example.stranger.model.ItemHome
//import com.example.stranger.repository.Repository
//import gun0912.tedimagepicker.util.ToastUtil
//import java.io.ByteArrayOutputStream
//
//class MyService : Service() {
//    private  val repository: Repository  = Repository()
//    private lateinit var itemHome: ItemHome
//    private var bitmapList : ArrayList<ByteArray> = arrayListOf()
//    private val preferences: Preferences by lazy {
//        Preferences.getInstance(ToastUtil.context)
//    }
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        if (intent?.extras?.get(TITLE) != null  || intent?.extras?.get(LIST_IMG)!=null ){
//            val title:String  = intent?.getStringExtra(TITLE) as String
//            val uriList:ArrayList<Uri> = intent?.getParcelableArrayListExtra<Uri>(LIST_IMG) as ArrayList<Uri>
//            val userName : String = intent?.extras!!.get(USER_NAME) as String
//            for (i in uriList!!){
//                var bitmap : Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,i )
//                val baos = ByteArrayOutputStream()
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//                var data = baos.toByteArray()
//                bitmapList.add(data)
//            }
//            val key :String = repository.getkey()
//            repository.upLoadImgList(uriList,object : missionListImg{
//                override fun success(urlList: ArrayList<String>) {
//                    var item = ItemHome(key,preferences.getUserId().toString(),userName,)
//                    repository.newItemPostHome(item,key,object : mission{
//                        override fun succsess() {
//                            notification()
//
//                        }
//
//                        override fun failuer() {
//
//                        }
//
//                    })
//                }
//
//                override fun failure(exception: Exception) {
//
//                }
//
//                override fun load() {
//
//                }
//
//            })
//
//        }
//        return START_REDELIVER_INTENT
//    }
//    fun notification(){
//
//        val   builder = NotificationCompat.Builder(this)
//            .setContentText("upload sucssec")
//            .build()
//        val notificationManagerCompat  = NotificationManagerCompat.from(this)
//        notificationManagerCompat.notify(1, builder)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//    }
//
//    override fun onBind(intent: Intent): IBinder {
//        TODO("Return the communication channel to the service.")
//    }
//    companion object{
//        private val LIST_IMG = "LIST_IMG"
//        private val TITLE="TITLE"
//        private val USER_NAME = "NAME"
//    }
//}