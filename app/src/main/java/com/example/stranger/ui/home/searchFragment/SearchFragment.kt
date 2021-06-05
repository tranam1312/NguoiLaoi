package com.example.stranger.ui.home.fragment.sreachFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.stranger.R
import com.example.stranger.databinding.SearchFragmentBinding

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }
    private lateinit var binding: SearchFragmentBinding
    private val viewModel: SearchViewModel by lazy {
        ViewModelProvider(this).get(SearchViewModel::class.java)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.search_fragment, container, false)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarSearch)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return  binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem)= when(item.itemId){
        android.R.id.home->{
            requireActivity().supportFragmentManager.popBackStack()
            true
        }
        else-> super.onOptionsItemSelected(item)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}