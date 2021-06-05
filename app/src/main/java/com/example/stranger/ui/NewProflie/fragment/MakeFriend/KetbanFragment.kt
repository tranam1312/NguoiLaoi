package com.example.stranger.ui.NewProflie.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.stranger.R
import com.example.stranger.databinding.FragmentKetbanBinding
import com.example.stranger.mode.ProFile
import com.example.stranger.mode.Uid
import com.example.stranger.ui.Home

class KetbanFragment : Fragment() {
    private val makeFriendViewModel : MakeFriendViewModel by lazy {
        ViewModelProvider(requireActivity()).get(MakeFriendViewModel::class.java)
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var makeFriendAdapter: MakeFriendAdapter
    private lateinit var binding:FragmentKetbanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_ketban,container,false)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarKetban)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.make_friend_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem)= when(item.itemId){
        R.id.skip->{
            val intent = Intent(requireContext(), Home::class.java )
            startActivity(intent)
            true
        }
        android.R.id.home -> {
            requireActivity().finish()
            true
        }
        else-> super.onOptionsItemSelected(item)
    }
    private fun init(){
        recyclerView = binding.recyclerviewketban
        makeFriendAdapter  = MakeFriendAdapter(addOnClick, onCorpseClick, onCLickDeleteAddRequest, onClickDeleteConfirmRequest, onClickItemAddRequest, onClickItemConfirmRequest)
    }
    private  val addOnClick :(ProFile) ->Unit={

    }
    private val onCorpseClick :(Uid) ->Unit={

    }
    private  val onCLickDeleteAddRequest :(ProFile) -> Unit={

    }
    private val onClickDeleteConfirmRequest:(Uid)->Unit={

    }
    private val onClickItemAddRequest:(ProFile) ->Unit ={

    }
    private val onClickItemConfirmRequest:(Uid)->Unit ={

    }
    companion object {
        fun newInstance()= KetbanFragment()
        const val TAG = "KET_BAN_FRAGMENT"
    }
}