package com.example.stranger.ui.messenger

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.stranger.R
import com.example.stranger.databinding.MessengerFragmentBinding

class MessengerFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = MessengerFragment()
        val TAG ="MESSENGER_FRAGMENT"
    }
    private lateinit var binding:MessengerFragmentBinding
    private lateinit var viewModel: MessengerViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.messenger_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MessengerViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.messenger_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem)= when(item.itemId) {
        else-> super.onOptionsItemSelected(item)
    }


}