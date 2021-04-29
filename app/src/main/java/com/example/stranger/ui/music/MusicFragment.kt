package com.example.stranger.ui.music

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.stranger.R
import com.example.stranger.databinding.MusicFragmentBinding

class MusicFragment : Fragment() {

    companion object {
        fun newInstance() = MusicFragment()
    }

    private lateinit var binding: MusicFragmentBinding
    private val viewModel: MusicViewModel by lazy {
        ViewModelProvider(this).get(MusicViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.music_fragment,container,false)
        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.music_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem)= when(item.itemId) {
        else-> super.onOptionsItemSelected(item)
    }



}