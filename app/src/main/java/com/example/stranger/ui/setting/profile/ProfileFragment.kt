package com.example.stranger.ui.setting.fragment.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.stranger.R
import com.example.stranger.databinding.BottomSheetMoreBinding
import com.example.stranger.databinding.ProfileFragmentBinding
import com.example.stranger.mode.ProFile
import com.example.stranger.ui.home.fragment.sreachFragment.SearchFragment
import com.google.android.material.bottomsheet.BottomSheetDialog

class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this).get(ProfileViewModel::class.java)
    }
    private lateinit var bottomSheetMoreBinding: BottomSheetMoreBinding
    private lateinit var binding: ProfileFragmentBinding
    private lateinit var moreBottomSheetDialog: BottomSheetDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =DataBindingUtil.inflate(layoutInflater,R.layout.profile_fragment, container, false)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarPersonalPage)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
       inflater.inflate(R.menu.pro_file_menu, menu)
    }

    override fun  onOptionsItemSelected(item: MenuItem)= when(item.itemId){
        R.id.search ->{
            requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in,R.anim.fade_out,R.anim.fade_in, R.anim.silde_out)
                .replace(android.R.id.content, SearchFragment.newInstance()).addToBackStack(TAG).commit()
            true
        }
        R.id.more->{
            showMoreBottomSheet()
            true
        }
        android.R.id.home->{
            requireActivity().finish()
            true
        }
        else ->super.onOptionsItemSelected(item)
    }
    private fun init(){
        viewModel.profile(viewModel.getUserLogin().uid,object : proFileMission{
            override fun succsess(proFile: ProFile) {
                binding.whereFrom.text = "From ${proFile.from}"
                binding.liveAt.text = "Live at ${proFile.liveAt}"
                binding.sex.text = proFile.sex
                binding.name.text = proFile.name
                Glide.with(requireContext()).load(proFile.imgUrlAvatar).diskCacheStrategy(
                    DiskCacheStrategy.AUTOMATIC).into(binding.imgAvatar)
            }
        })
    }

    private fun showMoreBottomSheet(){
        moreBottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetMoreBinding = DataBindingUtil.inflate(layoutInflater, R.layout.bottom_sheet_more, null, false)
        moreBottomSheetDialog.setContentView(bottomSheetMoreBinding.root)
        bottomSheetMoreBinding.edit.setOnClickListener {

        }
        bottomSheetMoreBinding.view.setOnClickListener {

        }
        bottomSheetMoreBinding.search.setOnClickListener {

        }
        bottomSheetMoreBinding.copy.setOnClickListener {

        }
        moreBottomSheetDialog.show()
    }
    companion object {
        fun newInstance() = ProfileFragment()
        val TAG = "PROFILE_FRAGMENT"
    }


}