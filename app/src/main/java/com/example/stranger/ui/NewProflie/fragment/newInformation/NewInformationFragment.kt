package com.example.stranger.ui.NewProflie.fragment


import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.stranger.*
import com.example.stranger.databinding.BottomSelectAvatarBinding
import com.example.stranger.databinding.FragmentNewInformationBinding
import com.example.stranger.databinding.ProgressDialogBinding
import com.example.stranger.local.Preferences
import com.example.stranger.mode.ProFile
import com.example.stranger.ui.NewProflie.fragment.MakeFriend.KetbanFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import java.io.ByteArrayOutputStream


@Suppress("DEPRECATION")
class NewInformationFragment : Fragment() {
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var bottomSelectAvatarBinding: BottomSelectAvatarBinding
    private lateinit var binding: FragmentNewInformationBinding
    private val viewmodel: NewProflieViewModel by lazy {
        ViewModelProvider(this).get(NewProflieViewModel::class.java)
    }
    private val preferences: Preferences by lazy {
        Preferences.getInstance(requireContext())
    }
    private lateinit var key: String
    private  var sex:String="female"
    private lateinit var progressbinding: ProgressDialogBinding
    private lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.fragment_new_information,
                container,
                false
        )
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarInf)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Complete information"
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.compelte_infomation_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            requireActivity().finish()
            true
        }
        R.id.post -> {
            postProFile()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Glide.with(binding.root.context).load(viewmodel.getUserLogin().photoUrl).into(binding.imgAvtar)
        binding.name.setText(viewmodel.getUserLogin().displayName)
        binding.email.setText(viewmodel.getUserLogin().email)
        if (sex == "female"){
            binding.female.isChecked= true
        }
        binding.female.setOnClickListener{
            selectFemale()
        }
        binding.male.setOnClickListener {
            selectMale()
        }
        binding.imgAvtar.setOnClickListener {
            checkPermission()
        }

        checkNumberPhone()
        checkCountry()
    }

    private fun checkPermission() {
        TedPermission.with(context)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
                .check();
    }

    val permissionListener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            showDiaLog()
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {

        }

    }

    private fun showDiaLog() {
        bottomSheetDialog = BottomSheetDialog(requireActivity())
        bottomSelectAvatarBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.bottom_select_avatar,
                null,
                false
        )
        bottomSheetDialog.setContentView(bottomSelectAvatarBinding.root)
        bottomSelectAvatarBinding.camera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_CAMERA_CODE)
        }
        bottomSelectAvatarBinding.library.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_PICK_CODE)
        }
        bottomSheetDialog.show()
    }


    fun postProFile() {
        key = viewmodel.getkey()
        binding.imgAvtar.isDrawingCacheEnabled = true
        binding.imgAvtar.buildDrawingCache()
        val bitmap = (binding.imgAvtar.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        showLoad()
        viewmodel.uploaimg(data ,key, object : missionImg {
            override fun success(Url: String) {
                newProfile(Url)
            }
            override fun failure(exception: Exception) {
                Toast.makeText(requireContext(),"$exception",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun newProfile( url: String) {
        val name :String = binding.name.text.toString()
        val numberPhone:String =binding.numberPhone.text.toString()
        val day:String = binding.day.text.toString()
        val month:String =  binding.month.text.toString()
        val  year:String =binding.year.text.toString()
        val from:String= binding.from.text.toString()
        val liveAt : String = binding.liveAt.text.toString()
        Log.d("kkk","$name")
        var profile = ProFile(viewmodel.getUserLogin().uid, name,day,month,year,sex,numberPhone,url,from, liveAt)
        viewmodel.newProfile(profile, object : mission {
            override fun succsess() {
                dialog.dismiss()
                requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out,R.anim.fade_in, R.anim.silde_out).replace(android.R.id.content,
                    KetbanFragment.newInstance()).addToBackStack("hello").commit()
            }
            override fun failuer() {
                TODO("Not yet implemented")
            }

        })

    }

    fun showLoad() {
        dialog = Dialog(requireContext())
        progressbinding = DataBindingUtil.inflate(layoutInflater, R.layout.progress_dialog, null, false)
        dialog.setContentView(progressbinding.root)
        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        Log.d("kk", "$key")
        dialog.show()
    }

    private fun checkNumberPhone() {
        binding.numberPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 10) {
                    var number: String = binding.numberPhone.text.toString().trim()
                    var sdt: String = number.substring(0, 3)
                    if (firstNumber.contains(sdt)) {
                        binding.numberPhone.setError(null)

                    } else {
                        binding.numberPhone.setError("number cannot phone number")
                    }
                } else if (s?.length!! < 10) {
                    binding.numberPhone.setError("number cannot phone number")
                }
            }
            override fun afterTextChanged(s: Editable?) {

            }

        })


    }

    private fun checkCountry() {
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, country)
        binding.from.setAdapter(arrayAdapter)
        binding.liveAt.setAdapter(arrayAdapter)
    }
    private fun selectFemale(){
        binding.female.isChecked = true
        sex= binding.female.text.toString()
        if (binding.male.isChecked){
            binding.male.isChecked = false
        }
    }
    private  fun selectMale(){
        binding.male.isChecked = true
        sex = binding.male.text.toString()
        if (binding.female.isChecked){
            binding.female.isChecked = false
        }
    }

    companion object {
        fun newInstance()= NewInformationFragment()
        val REQUEST_CAMERA_CODE= 200
        val REQUEST_PICK_CODE =120
        private val firstNumber: ArrayList<String> = arrayListOf("086", "096", "097", "098", "032", "033", "034", "035", "036", "037", "038", "039", "091", "094", "083", "084", "085", "081", "082")
        private val country : ArrayList<String> = arrayListOf("Điện Biên ", "Sơn La ", "Hòa Bình ", "Lai Châu ", "Lào Cai",   "Yên Bái ", "An Giang ", "Bà Rịa Vũng Tàu ", "Bạc Liêu ", "Bắc Kạn ", "Bắc Ninh ",
            "Bắc Giang ", "Bến Tre ", "Bình Dương ", "Bình Định ", "Bình Phước ", "Bình Thuận ", "Cà Mau ", "Cao Bằng ", "Cần Thơ ", "Đà Nẵng ", "Đắk Lắk ", "Đắk Nông "
            , "Đồng Nai ", "Đồng Tháp ", "Gia Lai ", "Hà Giang ", "Hà Nam ", "Hà Nội ", "Hà Tây ", "Hà Tĩnh ", "Hải Phòng ", "Hải Dương ", "Hồ Chí Minh ", "Hậu Giang ", "Hưng Yên ",
            "Khánh Hòa ", "Kiên Giang ", "Kon Tum ", "Lạng Sơn ", "Lâm Đồng ", "Long An ", "Nam Định ", "Nghệ An ", "Ninh Bình ", "Ninh Thuận ", "Phú Thọ ", "Phú Yên ", "Quảng Bình ", "Quảng Nam ",
            "Quảng Ninh ", "Quảng Ngãi ", "Quảng Trị ", "Sóc Trăng ", "Tây Ninh ", "Thái Bình ", "Thái Nguyên ", "Thanh Hóa ", "Thừa Thiên Huế ", "Tiền Giang ", "Trà Vinh ", "Tuyên Quang ", "Vĩnh Long ",
            "Vĩnh Phúc ")
    }
}